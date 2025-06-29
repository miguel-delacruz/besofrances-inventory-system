// Script para mostrar solo la sección seleccionada
const secciones = {
    'categorias': () => {
        recargarCategorias();
        recargarSelectCategorias();
    },
    'nuevo-producto': () => {
        recargarSelectCategorias();
        recargarProductosSimples();
    },
    'cierre-stock': () => {
        recargarCierreStock();
    },
    'movimientos': () => {
        recargarMovimientos();
    }
};

document.querySelectorAll('.sidebar nav a').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        // Quitar active de todos
        document.querySelectorAll('.sidebar nav a').forEach(l => l.classList.remove('active'));
        this.classList.add('active');
        // Ocultar todas las secciones
        document.querySelectorAll('.main-content .content-section').forEach(sec => sec.style.display = 'none');
        // Mostrar la seleccionada
        const id = this.getAttribute('href').replace('#','');
        document.getElementById(id).style.display = 'block';
        // Recargar datos si corresponde
        if (secciones[id]) secciones[id]();
    });
});

// Mostrar solo la primera sección al cargar
// Y recargar datos de la primera sección

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.main-content .content-section').forEach((sec, i) => {
        sec.style.display = (i === 0) ? 'block' : 'none';
        if (i === 0) {
            const id = sec.getAttribute('id');
            if (secciones[id]) secciones[id]();
        }
    });
});

// Referencias a elementos de la sección Ingreso de Productos
const productosTbodyIngreso = document.getElementById('ingreso-productos-list');
const buscarInputIngresoProducto = document.getElementById('buscar_ingreso_producto');

// NUEVO: Referencias a los inputs de búsqueda de Categoría y Nuevo Producto
const buscarInputCategoria = document.getElementById('buscar_categoria');
const buscarInputNuevoProducto = document.getElementById('buscar_producto');

// Función para abrir el modal de Ingreso de Stock
function abrirIngresoStockModal(productoId) {
    const ingresoStockModal = document.getElementById('ingreso-stock-modal');
    const inputIngresoStockProductoId = document.getElementById('ingreso-stock-producto-id');
    if (ingresoStockModal && inputIngresoStockProductoId) {
        inputIngresoStockProductoId.value = productoId;
        ingresoStockModal.style.display = 'block';
    }
}

// Función para cerrar el modal de Ingreso de Stock
function cerrarIngresoStockModal() {
    const ingresoStockModal = document.getElementById('ingreso-stock-modal');
    const formIngresoStock = document.getElementById('form-ingreso-stock');
    if (ingresoStockModal && formIngresoStock) {
        ingresoStockModal.style.display = 'none';
        formIngresoStock.reset();
    }
}

// Función para recargar la tabla de productos en la sección de Ingreso de Productos
function recargarIngresoProductos(filtro = '') {
    let url = contextPath + '/api/productos?action=listar_productos_simples';
    if (filtro) {
        url += '&buscar=' + encodeURIComponent(filtro);
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.ok) {
                productosTbodyIngreso.innerHTML = '';
                data.productos.forEach(producto => {
                    const tr = document.createElement('tr');
                    tr.dataset.id = producto.id_producto;
                    tr.innerHTML = `
                        <td>${producto.id_producto}</td>
                        <td>${producto.nombre_producto}</td>
                        <td>
                            <button class="btn-ingresar-stock" data-id="${producto.id_producto}">Ingresar Stock</button>
                        </td>
                    `;
                    productosTbodyIngreso.appendChild(tr);
                });
                // Event listeners para los botones de Ingresar Stock
                const ingresarStockButtons = productosTbodyIngreso.querySelectorAll('.btn-ingresar-stock');
                ingresarStockButtons.forEach(button => {
                    button.addEventListener('click', (e) => {
                        const productoId = e.target.dataset.id;
                        abrirIngresoStockModal(productoId);
                    });
                });
            }
        });
}

// Evento para buscar productos en la sección Ingreso de Productos
if (buscarInputIngresoProducto) {
    buscarInputIngresoProducto.addEventListener('input', function() {
        recargarIngresoProductos(this.value);
    });
}

// NUEVO: Evento para buscar categorías
if (buscarInputCategoria) {
    buscarInputCategoria.addEventListener('input', function() {
        recargarCategorias(this.value);
    });
}

// NUEVO: Evento para buscar productos en 'Nuevo Producto'
if (buscarInputNuevoProducto) {
    buscarInputNuevoProducto.addEventListener('input', function() {
        recargarProductosSimples(this.value);
    });
}

// Recargar la tabla al cargar la página si la sección está visible
if (productosTbodyIngreso) {
    recargarIngresoProductos();
}

// Referencias a elementos de la sección Productos
const productosTbodyListado = document.getElementById('productos-listado');
const buscarInputProductosListado = document.getElementById('buscar_productos_listado');

// Modales y formularios para Cambiar Estado y Merma
function abrirCambioEstadoModal(productoId, detalleId, estadoActual) {
    const cambioEstadoModal = document.getElementById('cambio-estado-modal');
    const inputProductoId = document.getElementById('cambio-estado-producto-id');
    const inputDetalleId = document.getElementById('cambio-estado-detalle-id');
    const inputEstadoActual = document.getElementById('cambio-estado-actual');
    const textoEstadoActual = document.getElementById('cambio-estado-actual-texto');
    const textoEstadoNuevo = document.getElementById('cambio-estado-nuevo-texto');
    if (cambioEstadoModal && inputProductoId && inputDetalleId && inputEstadoActual) {
        inputProductoId.value = productoId;
        inputDetalleId.value = detalleId;
        inputEstadoActual.value = estadoActual;
        textoEstadoActual.textContent = estadoActual;
        textoEstadoNuevo.textContent = estadoActual === 'congelado' ? 'refrigerado' : 'congelado';
        cambioEstadoModal.style.display = 'block';
    }
}

function cerrarCambioEstadoModal() {
    const cambioEstadoModal = document.getElementById('cambio-estado-modal');
    const formCambioEstado = document.getElementById('form-cambio-estado');
    if (cambioEstadoModal && formCambioEstado) {
        cambioEstadoModal.style.display = 'none';
        formCambioEstado.reset();
    }
}

function abrirMermaModal(detalleId, productoId) {
    const mermaModal = document.getElementById('merma-modal');
    const inputDetalleId = document.getElementById('merma-detalle-id');
    const inputProductoId = document.getElementById('merma-producto-id');
    if (mermaModal && inputDetalleId && inputProductoId) {
        inputDetalleId.value = detalleId;
        inputProductoId.value = productoId;
        mermaModal.style.display = 'block';
    }
}

function cerrarMermaModal() {
    const mermaModal = document.getElementById('merma-modal');
    const formMerma = document.getElementById('form-merma');
    if (mermaModal && formMerma) {
        mermaModal.style.display = 'none';
        formMerma.reset();
    }
}

// Función para recargar la tabla de productos en la sección de Listado de Productos
function recargarListadoProductos(filtro = '') {
    let url = contextPath + '/api/productos?action=listar';
    if (filtro) {
        url += '&buscar=' + encodeURIComponent(filtro);
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.ok) {
                productosTbodyListado.innerHTML = '';
                data.productos.forEach(producto => {
                    const tr = document.createElement('tr');
                    tr.dataset.productoId = producto.id_producto;
                    tr.dataset.detalleId = producto.id_detalle;
                    tr.innerHTML = `
                        <td>${producto.id_detalle || 'N/A'}</td>
                        <td>${producto.nombre_producto}</td>
                        <td>${producto.fecha_ingreso || 'No disponible'}</td>
                        <td>${producto.fecha_vencimiento || 'No disponible'}</td>
                        <td>${producto.estado || 'No disponible'}</td>
                        <td>${producto.cantidad || '0'}</td>
                        <td>
                            <button class="btn-cambiar-estado" data-producto-id="${producto.id_producto}" data-detalle-id="${producto.id_detalle}" data-estado="${producto.estado}">Cambiar Estado</button>
                            <button class="btn-mermar" data-producto-id="${producto.id_producto}" data-detalle-id="${producto.id_detalle}">Mermar</button>
                        </td>
                    `;
                    productosTbodyListado.appendChild(tr);
                });
                // Eventos para los botones de Cambiar Estado
                const cambiarEstadoButtons = productosTbodyListado.querySelectorAll('.btn-cambiar-estado');
                cambiarEstadoButtons.forEach(button => {
                    button.addEventListener('click', (e) => {
                        const productoId = e.target.dataset.productoId;
                        const detalleId = e.target.dataset.detalleId;
                        const estadoActual = e.target.dataset.estado;
                        abrirCambioEstadoModal(productoId, detalleId, estadoActual);
                    });
                });
                // Eventos para los botones de Merma
                const mermarButtons = productosTbodyListado.querySelectorAll('.btn-mermar');
                mermarButtons.forEach(button => {
                    button.addEventListener('click', (e) => {
                        const productoId = e.target.dataset.productoId;
                        const detalleId = e.target.dataset.detalleId;
                        abrirMermaModal(detalleId, productoId);
                    });
                });
            }
        });
}

// Evento para buscar productos en la sección Listado de Productos
if (buscarInputProductosListado) {
    buscarInputProductosListado.addEventListener('input', function() {
        recargarListadoProductos(this.value);
    });
}

// Recargar la tabla al cargar la página si la sección está visible
if (productosTbodyListado) {
    recargarListadoProductos();
}

// Envío del formulario de Cambiar Estado
const formCambioEstado = document.getElementById('form-cambio-estado');
if (formCambioEstado) {
    formCambioEstado.onsubmit = (e) => {
        e.preventDefault();
        const productoId = document.getElementById('cambio-estado-producto-id').value;
        const detalleId = document.getElementById('cambio-estado-detalle-id').value;
        const estadoActual = document.getElementById('cambio-estado-actual').value;
        const fechaVencimiento = document.getElementById('cambio-estado-fecha-vencimiento').value;
        const cantidad = document.getElementById('cambio-estado-cantidad').value;
        const nuevoEstado = estadoActual === 'congelado' ? 'refrigerado' : 'congelado';
        
        fetch(contextPath + '/api/productos?action=cambiar_estado', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                id_producto: productoId,
                id_detalle: detalleId,
                estado_actual: estadoActual,
                nuevo_estado: nuevoEstado,
                fecha_vencimiento: fechaVencimiento,
                cantidad: cantidad
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.ok) {
                alert('Estado cambiado correctamente!');
                cerrarCambioEstadoModal();
                recargarListadoProductos();
            } else {
                alert('Error al cambiar estado: ' + data.error);
            }
        });
    };
}

// Envío del formulario de Merma
const formMerma = document.getElementById('form-merma');
if (formMerma) {
    formMerma.onsubmit = (e) => {
        e.preventDefault();
        const detalleId = document.getElementById('merma-detalle-id').value;
        const productoId = document.getElementById('merma-producto-id').value;
        const cantidad = document.getElementById('merma-cantidad').value;
        const motivo = document.getElementById('merma-motivo').value;
        
        fetch(contextPath + '/api/productos?action=mermar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
                id_detalle: detalleId,
                id_producto: productoId,
                cantidad: cantidad,
                motivo: motivo
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.ok) {
                alert('Merma registrada correctamente!');
                cerrarMermaModal();
                recargarListadoProductos();
            } else {
                alert('Error al registrar merma: ' + data.error);
            }
        });
    };
}

// Función para recargar la tabla de categorías
function recargarCategorias(filtro = '') {
    let url = contextPath + '/api/categorias?action=listar';
    if (filtro) {
        url += '&buscar=' + encodeURIComponent(filtro);
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.ok) {
                const categoriasTbody = document.getElementById('categorias-list');
                if (categoriasTbody) {
                    categoriasTbody.innerHTML = '';
                    data.categorias.forEach(categoria => {
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                            <td>${categoria.id_categoria}</td>
                            <td>${categoria.nombre_categoria}</td>
                            <td>
                                <button class="btn-editar-categoria" data-id="${categoria.id_categoria}" data-nombre="${categoria.nombre_categoria}">Editar</button>
                                <button class="btn-eliminar-categoria" data-id="${categoria.id_categoria}">Eliminar</button>
                            </td>
                        `;
                        categoriasTbody.appendChild(tr);
                    });
                }
            }
        });
}

// Función para recargar el select de categorías en Nuevo Producto y en edición
function recargarSelectCategorias() {
    let url = contextPath + '/api/categorias?action=listar';
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if (data.ok) {
                // Para el select de nuevo producto
                const selectNuevo = document.getElementById('categoria_producto');
                if (selectNuevo) {
                    selectNuevo.innerHTML = '<option value="">Seleccionar Categoría</option>';
                    data.categorias.forEach(categoria => {
                        const option = document.createElement('option');
                        option.value = categoria.id_categoria;
                        option.textContent = categoria.nombre_categoria;
                        selectNuevo.appendChild(option);
                    });
                }
                // Para el select de edición de producto
                const selectEditar = document.getElementById('edit_categoria_producto');
                if (selectEditar) {
                    selectEditar.innerHTML = '<option value="">Seleccionar Categoría</option>';
                    data.categorias.forEach(categoria => {
                        const option = document.createElement('option');
                        option.value = categoria.id_categoria;
                        option.textContent = categoria.nombre_categoria;
                        selectEditar.appendChild(option);
                    });
                }
            }
        });
}

// Cierre de stock: recargar solo al cambiar la fecha
const inputFechaCierre = document.getElementById('buscar_cierre_fecha');
if (inputFechaCierre) {
    inputFechaCierre.addEventListener('change', function() {
        recargarCierreStock(this.value);
    });
}

// Movimientos: recargar solo al cambiar la fecha
const inputFechaMovimientos = document.getElementById('buscar_movimientos_fecha');
if (inputFechaMovimientos) {
    inputFechaMovimientos.addEventListener('change', function() {
        recargarMovimientos(this.value);
    });
}

// Mostrar mensaje si no hay datos en cierre de stock
function recargarCierreStock(fecha = '') {
    let url = contextPath + '/api/productos?action=listar_cierres_stock';
    if (fecha) {
        url += '&fecha=' + encodeURIComponent(fecha);
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const cierreStockTbody = document.getElementById('cierre-stock-list');
            if (cierreStockTbody) {
                cierreStockTbody.innerHTML = '';
                if (data.ok && data.cierres.length > 0) {
                    data.cierres.forEach(cierre => {
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                            <td>${cierre.id_cierre}</td>
                            <td>${cierre.fecha_cierre}</td>
                            <td>${cierre.responsable}</td>
                            <td>${cierre.nombre_producto}</td>
                            <td>${cierre.estado}</td>
                            <td>${cierre.cantidad}</td>
                        `;
                        cierreStockTbody.appendChild(tr);
                    });
                } else {
                    const tr = document.createElement('tr');
                    tr.innerHTML = '<td colspan="6">No hay datos para la fecha seleccionada.</td>';
                    cierreStockTbody.appendChild(tr);
                }
            }
        });
}

// Mostrar mensaje si no hay datos en movimientos
function recargarMovimientos(fecha = '') {
    let url = contextPath + '/api/productos?action=listar_movimientos';
    if (fecha) {
        url += '&fecha=' + encodeURIComponent(fecha);
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const movimientosTbody = document.getElementById('movimientos-list');
            if (movimientosTbody) {
                movimientosTbody.innerHTML = '';
                if (data.ok && data.movimientos.length > 0) {
                    data.movimientos.forEach(mov => {
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                            <td>${mov.id_historial}</td>
                            <td>${mov.fecha_hora}</td>
                            <td>${mov.responsable}</td>
                            <td>${mov.nombre_producto}</td>
                            <td>${mov.tipo_accion}</td>
                            <td>${mov.motivo}</td>
                            <td>${mov.cantidad}</td>
                            <td>${mov.estado_anterior}</td>
                            <td>${mov.estado_nuevo}</td>
                        `;
                        movimientosTbody.appendChild(tr);
                    });
                } else {
                    const tr = document.createElement('tr');
                    tr.innerHTML = '<td colspan="9">No hay datos para la fecha seleccionada.</td>';
                    movimientosTbody.appendChild(tr);
                }
            }
        });
}

// Función para recargar la tabla de productos en 'Nuevo Producto'
function recargarProductosSimples(filtro = '') {
    let url = contextPath + '/api/productos?action=listar_productos_simples';
    if (filtro) {
        url += '&buscar=' + encodeURIComponent(filtro);
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const productosTbody = document.getElementById('productos-list');
            if (productosTbody) {
                productosTbody.innerHTML = '';
                if (data.ok && data.productos.length > 0) {
                    data.productos.forEach(producto => {
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
                            <td>${producto.id_producto}</td>
                            <td>${producto.nombre_producto}</td>
                            <td>${producto.nombre_categoria || ''}</td>
                            <td>
                                <button class="btn-editar-producto" data-id="${producto.id_producto}" data-nombre="${producto.nombre_producto}" data-id-categoria="${producto.id_categoria}">Editar</button>
                                <button class="btn-eliminar-producto" data-id="${producto.id_producto}">Eliminar</button>
                            </td>
                        `;
                        productosTbody.appendChild(tr);
                    });
                } else {
                    const tr = document.createElement('tr');
                    tr.innerHTML = '<td colspan="4">No hay productos registrados.</td>';
                    productosTbody.appendChild(tr);
                }
            }
        });
} 
// Script para mostrar solo la sección seleccionada
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
    });
});

// Mostrar solo la primera sección al cargar
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.main-content .content-section').forEach((sec, i) => {
        sec.style.display = (i === 0) ? 'block' : 'none';
    });
});

// Referencias a elementos de la sección Ingreso de Productos
const productosTbodyIngreso = document.getElementById('ingreso-productos-list');
const buscarInputIngresoProducto = document.getElementById('buscar_ingreso_producto');

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
    let url = '${pageContext.request.contextPath}/api/productos?action=listar_productos_simples';
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
    let url = '${pageContext.request.contextPath}/api/productos?action=listar';
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
        
        fetch('${pageContext.request.contextPath}/api/productos?action=cambiar_estado', {
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
        
        fetch('${pageContext.request.contextPath}/api/productos?action=mermar', {
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
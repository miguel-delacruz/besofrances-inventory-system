/**
 * Funcionalidad para selección de perfil
 * Equivalente al JavaScript del archivo PHP original
 */

function seleccionarPerfil(tipo) {
    // Guardar el perfil seleccionado en sessionStorage
    sessionStorage.setItem('perfil', tipo);
    
    // Mostrar indicador de carga
    mostrarCargando();
    
    // Redirigir al login JSP
    setTimeout(function() {
        window.location.href = getContextPath() + '/login';
    }, 500);
}

function mostrarCargando() {
    // Crear overlay de carga
    const overlay = document.createElement('div');
    overlay.id = 'loading-overlay';
    overlay.style.cssText = `
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(255, 255, 255, 0.9);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 9999;
    `;
    
    const spinner = document.createElement('div');
    spinner.style.cssText = `
        width: 50px;
        height: 50px;
        border: 4px solid #f3f3f3;
        border-top: 4px solid #c90076;
        border-radius: 50%;
        animation: spin 1s linear infinite;
    `;
    
    const style = document.createElement('style');
    style.textContent = `
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    `;
    
    document.head.appendChild(style);
    overlay.appendChild(spinner);
    document.body.appendChild(overlay);
}

function getContextPath() {
    // Obtener el contexto de la aplicación
    return window.location.pathname.substring(0, window.location.pathname.indexOf("/", 1));
}

// Verificar si ya hay un perfil guardado al cargar la página
window.onload = function() {
    const perfilGuardado = sessionStorage.getItem('perfil');
    if (perfilGuardado) {
        // Si ya hay un perfil guardado, redirigir directamente
        window.location.href = getContextPath() + '/login';
    }
};

// Agregar efectos de hover mejorados
document.addEventListener('DOMContentLoaded', function() {
    const opciones = document.querySelectorAll('.opcion');
    
    opciones.forEach(function(opcion) {
        opcion.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-4px) scale(1.04)';
        });
        
        opcion.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0) scale(1)';
        });
        
        opcion.addEventListener('click', function() {
            const tipo = this.querySelector('span').textContent.toLowerCase() === 'administrador' ? 'admin' : 'usuario';
            seleccionarPerfil(tipo);
        });
    });
}); 
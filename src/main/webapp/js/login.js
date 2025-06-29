/**
 * Funcionalidad para la página de login
 * Equivalente al login.js del archivo PHP original
 */

document.addEventListener('DOMContentLoaded', function() {
    // Obtener el perfil guardado en sessionStorage
    var perfil = sessionStorage.getItem('perfil');
    if (perfil) {
        document.getElementById('perfilInput').value = perfil;
    }
    
    // Agregar validación del formulario
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            // Verificar que se haya seleccionado un perfil
            const perfilInput = document.getElementById('perfilInput');
            if (!perfilInput.value) {
                e.preventDefault();
                alert('Por favor, selecciona un perfil primero.');
                window.location.href = getContextPath() + '/seleccionar_perfil';
                return false;
            }
            
            // Verificar que los campos no estén vacíos
            const usuario = document.querySelector('input[name="usuario"]').value.trim();
            const password = document.querySelector('input[name="password"]').value.trim();
            
            if (!usuario || !password) {
                e.preventDefault();
                alert('Por favor, completa todos los campos.');
                return false;
            }
            
            // Mostrar indicador de carga
            mostrarCargando();
        });
    }
    
    // Agregar efectos visuales a los campos
    const inputs = document.querySelectorAll('input[type="text"], input[type="password"]');
    inputs.forEach(function(input) {
        input.addEventListener('focus', function() {
            this.parentElement.classList.add('focused');
        });
        
        input.addEventListener('blur', function() {
            if (!this.value) {
                this.parentElement.classList.remove('focused');
            }
        });
    });
});

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

// Función para limpiar sessionStorage al cerrar sesión
function limpiarSesion() {
    sessionStorage.removeItem('perfil');
}

// Agregar evento para limpiar sesión al cerrar la ventana
window.addEventListener('beforeunload', function() {
    // Solo limpiar si no se está enviando el formulario
    if (!document.getElementById('loginForm').checkValidity()) {
        limpiarSesion();
    }
}); 
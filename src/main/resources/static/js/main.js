/* ========================================
   JKards - Funcionalidades JavaScript
   ======================================== */

// Iconos SVG para password toggle
const ICONS = {
    eye: `<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
        <circle cx="12" cy="12" r="3"></circle>
    </svg>`,

    eyeOff: `<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path>
        <line x1="1" y1="1" x2="23" y2="23"></line>
    </svg>`
};

// Generar partículas sutiles
function createParticles() {
    const particlesContainer = document.querySelector('.particles');
    if (!particlesContainer) return;

    const particleCount = 30;

    for (let i = 0; i < particleCount; i++) {
        const particle = document.createElement('div');
        particle.className = 'particle';

        particle.style.left = Math.random() * 100 + '%';
        particle.style.animationDelay = Math.random() * 25 + 's';
        particle.style.animationDuration = (Math.random() * 15 + 20) + 's';

        particlesContainer.appendChild(particle);
    }
}

// Toggle password visibility con iconos SVG
function setupPasswordToggle() {
    const passwordInputs = document.querySelectorAll('input[type="password"]');

    passwordInputs.forEach(input => {
        const toggleButton = document.createElement('button');
        toggleButton.type = 'button';
        toggleButton.className = 'password-toggle';
        toggleButton.innerHTML = ICONS.eye;
        toggleButton.setAttribute('aria-label', 'Mostrar contraseña');

        toggleButton.addEventListener('click', () => {
            const type = input.getAttribute('type') === 'password' ? 'text' : 'password';
            input.setAttribute('type', type);
            toggleButton.innerHTML = type === 'password' ? ICONS.eye : ICONS.eyeOff;
            toggleButton.setAttribute('aria-label',
                type === 'password' ? 'Mostrar contraseña' : 'Ocultar contraseña'
            );
        });

        input.parentElement.style.position = 'relative';
        input.parentElement.appendChild(toggleButton);
    });
}

// Validación de formularios
function setupFormValidation() {
    const forms = document.querySelectorAll('form');

    forms.forEach(form => {
        const inputs = form.querySelectorAll('input[required]');

        inputs.forEach(input => {
            input.addEventListener('blur', function() {
                validateField(this);
            });

            input.addEventListener('input', function() {
                if (this.classList.contains('error')) {
                    validateField(this);
                }
            });
        });
    });
}

function validateField(field) {
    const value = field.value.trim();
    const errorElement = field.parentElement.querySelector('.form-error');

    if (!value && field.hasAttribute('required')) {
        field.classList.add('error');
        if (errorElement) {
            errorElement.textContent = 'Este campo es obligatorio';
        }
        return false;
    }

    if (field.type === 'email' && value) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(value)) {
            field.classList.add('error');
            if (errorElement) {
                errorElement.textContent = 'Ingresa un email válido';
            }
            return false;
        }
    }

    if (field.type === 'password' && value && value.length < 6) {
        field.classList.add('error');
        if (errorElement) {
            errorElement.textContent = 'Mínimo 6 caracteres';
        }
        return false;
    }

    field.classList.remove('error');
    if (errorElement) {
        errorElement.textContent = '';
    }
    return true;
}

// Auto-hide alerts
function setupAlerts() {
    const alerts = document.querySelectorAll('.alert');

    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            alert.style.transform = 'translateY(-10px)';
            setTimeout(() => alert.remove(), 300);
        }, 5000);
    });
}

// Navbar scroll effect
function setupNavbarScroll() {
    const navbar = document.querySelector('.landing-navbar');
    if (!navbar) return;

    window.addEventListener('scroll', () => {
        if (window.scrollY > 50) {
            navbar.style.background = 'rgba(10, 14, 26, 0.98)';
            navbar.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.3)';
        } else {
            navbar.style.background = 'rgba(10, 14, 26, 0.95)';
            navbar.style.boxShadow = 'none';
        }
    });
}

// Prevenir doble submit
function setupFormSubmission() {
    const forms = document.querySelectorAll('form');

    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            const submitButton = this.querySelector('button[type="submit"]');

            if (submitButton && !submitButton.disabled) {
                submitButton.disabled = true;
                const originalText = submitButton.innerHTML;
                submitButton.innerHTML = '<span class="loading"></span>';

                setTimeout(() => {
                    if (submitButton.disabled) {
                        submitButton.disabled = false;
                        submitButton.innerHTML = originalText;
                    }
                }, 3000);
            }
        });
    });
}

// Inicialización
document.addEventListener('DOMContentLoaded', () => {
    createParticles();
    setupPasswordToggle();
    setupFormValidation();
    setupAlerts();
    setupNavbarScroll();
    setupFormSubmission();

    console.log('🎨 JKards loaded successfully!');
});
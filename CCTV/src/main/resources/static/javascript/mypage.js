document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('logoutButton').addEventListener('click', function () {
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        window.location.href = '/index.html';
    });

    document.getElementById('deleteAccountButton').addEventListener('click', async function () {
        const userId = localStorage.getItem('userId');
        const token = localStorage.getItem('token');

        if (confirm('Are you sure you want to delete your account?')) {
            const response = await fetch(`/api/users/${userId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                localStorage.removeItem('token');
                localStorage.removeItem('userId');
                alert('Account deleted successfully');
                window.location.href = '/index.html';
            } else {
                alert('Failed to delete account');
            }
        }
    });
});

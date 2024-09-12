document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Please login first");
        window.location.href = "/login.html";
        return;
    }

    document.getElementById("shopForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const name = document.getElementById("name").value;
        const address = document.getElementById("address").value;

        try {
            const response = await fetch("/api/shops", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({ name, address })
            });

            if (response.ok) {
                alert("Shop registered successfully");
                window.location.href = "/shop.html";
            } else {
                const errorText = await response.text();
                alert(`Failed to register shop: ${errorText || 'No additional information available'}`);
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred while registering the shop. Please check your network connection and try again.");
        }
    });
});

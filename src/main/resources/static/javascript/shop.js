// shop.js

document.addEventListener("DOMContentLoaded", async function () {
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Please login first");
        window.location.href = "/login.html";
        return;
    }

    try {
        const response = await fetch("/api/shops", {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (response.ok) {
            const data = await response.json();
            const shopList = document.getElementById("shopList");
            data.forEach(shop => {
                const li = document.createElement("li");
                li.textContent = `${shop.name} - ${shop.address}`;
                shopList.appendChild(li);
            });
        } else {
            alert("Failed to fetch shops");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred");
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
                alert("Shop added successfully");
                window.location.reload();
            } else {
                alert("Failed to add shop");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred");
        }
    });
});

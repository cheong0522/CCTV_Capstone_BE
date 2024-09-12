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

            if (data.length === 0) {
                const noShopsMessage = document.createElement("li");
                noShopsMessage.textContent = "No shops registered.";
                shopList.appendChild(noShopsMessage);
            } else {
                data.forEach(shop => {
                    const li = document.createElement("li");
                    const a = document.createElement("a");
                    a.href = `/shop_detail.html?id=${shop.shopId}`;
                    a.textContent = `${shop.name} - ${shop.address}`;
                    li.appendChild(a);
                    shopList.appendChild(li);
                });
            }
        } else if (response.status === 403) {
            alert("You do not have permission to view this resource. Please login with the correct account.");
            window.location.href = "/login.html";
        } else {
            const errorText = await response.text();
            alert(`Failed to fetch shops: ${errorText || 'No additional information available'}`);
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred while fetching shops. Please check your network connection and try again.");
    }
});

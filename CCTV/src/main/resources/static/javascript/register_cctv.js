document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);
    const shopId = params.get("id");
    const token = localStorage.getItem("token");

    if (!shopId) {
        alert("No shop ID provided");
        window.location.href = "/shop.html";
        return;
    }

    if (!token) {
        alert("Please login first");
        window.location.href = "/login.html";
        return;
    }

    document.getElementById("registerCCTVForm").addEventListener("submit", async function (e) {
        e.preventDefault();

        const hlsUrl = document.getElementById("hlsUrl").value;

        if (!hlsUrl.trim()) {
            alert("HLS URL cannot be empty");
            return;
        }

        try {
            const response = await fetch(`/api/shops/${shopId}/cctv`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({ hlsUrls: [hlsUrl] })
            });

            if (response.ok) {
                alert("CCTV registered successfully");
                window.location.href = `/shop_detail.html?id=${shopId}`;
            } else if (response.status === 403) {
                alert("You do not have permission to register CCTV. Please login with the correct account.");
                window.location.href = "/login.html";
            } else {
                const errorText = await response.text();
                alert(`Failed to register CCTV: ${errorText || 'No additional information available'}`);
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred while registering the CCTV. Please check your network connection and try again.");
        }
    });
});

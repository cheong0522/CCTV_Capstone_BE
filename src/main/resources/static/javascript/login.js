document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const nickname = document.getElementById("nickname").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ nickname, password })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.response.token);
            alert("Login successful");
            window.location.href = "/shop.html";
        } else {
            alert("Login failed");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred");
    }
});

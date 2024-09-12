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
            localStorage.setItem("userId", data.response.userId); // userId 저장
            alert("Login successful");
            window.location.href = "/index.html"; // 메인 페이지로 리디렉션
        } else {
            alert("Login failed");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred");
    }
});

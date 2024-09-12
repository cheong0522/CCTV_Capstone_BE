document.getElementById("signupForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const nickname = document.getElementById("nickname").value;
    const password = document.getElementById("password").value;
    const phoneNumber = document.getElementById("phoneNumber").value.replace(/-/g, ''); // 하이픈 제거

    // 전화번호 유효성 검사
    if (phoneNumber.length > 11) {
        alert("Phone number must be 11 characters or less without hyphens.");
        return;
    }

    try {
        const response = await fetch("/api/signup", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ nickname, password, phoneNumber })
        });

        if (response.ok) {
            alert("Signup successful");
            window.location.href = "/login.html";
        } else {
            alert("Signup failed");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred");
    }
});

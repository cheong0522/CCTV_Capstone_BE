document.addEventListener("DOMContentLoaded", function () {
    const logoutButton = document.getElementById("logoutButton");
    const loginButton = document.getElementById("loginButton");

    if (localStorage.getItem("token")) {
        // 로그인이 되어 있을 때
        logoutButton.style.display = "block";
        loginButton.style.display = "none";
    } else {
        // 로그아웃 상태일 때
        logoutButton.style.display = "none";
        loginButton.style.display = "block";
    }

    logoutButton.addEventListener("click", function (e) {
        e.preventDefault();
        // 로컬 스토리지에서 JWT 토큰 제거
        localStorage.removeItem("token");
        alert("You have been logged out.");
        // 로그인 페이지로 리디렉션
        window.location.href = "/login.html";
    });
});

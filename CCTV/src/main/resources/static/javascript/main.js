document.addEventListener("DOMContentLoaded", function () {
    const loginButton = document.getElementById("loginButton");
    const mypageButton = document.getElementById("mypageButton");

    if (localStorage.getItem("token")) {
        // 로그인이 되어 있을 때
        loginButton.style.display = "none";
        mypageButton.style.display = "block";
    } else {
        // 로그아웃 상태일 때
        loginButton.style.display = "block";
        mypageButton.style.display = "none";
    }
});

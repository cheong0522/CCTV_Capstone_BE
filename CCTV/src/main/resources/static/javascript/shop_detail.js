document.addEventListener("DOMContentLoaded", async function () {
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

    try {
        const response = await fetch(`/api/shops/${shopId}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (response.ok) {
            const shop = await response.json();
            document.getElementById("shopName").textContent = shop.name;
            document.getElementById("shopAddress").textContent = shop.address;

            document.getElementById("registerCCTVButton").onclick = function() {
                location.href = `/register_cctv.html?id=${shopId}`;
            };

            document.getElementById("deleteShopButton").onclick = async function() {
                if (confirm("Are you sure you want to delete this shop? This action cannot be undone.")) {
                    try {
                        const deleteResponse = await fetch(`/api/shops/${shopId}`, {
                            method: "DELETE",
                            headers: {
                                "Authorization": `Bearer ${token}`
                            }
                        });
                        if (deleteResponse.ok) {
                            alert("Shop deleted successfully");
                            window.location.href = "/shop.html";
                        } else {
                            const errorText = await deleteResponse.text();
                            alert(`Failed to delete shop: ${errorText || 'No additional information available'}`);
                        }
                    } catch (error) {
                        console.error("Error:", error);
                        alert("An error occurred while deleting the shop. Please check your network connection and try again.");
                    }
                }
            };

            const cctvCountElement = document.getElementById("cctvCount");
            const cctvContainer = document.getElementById("cctvContainer");
            cctvContainer.style.display = "grid";
            cctvContainer.style.gridTemplateColumns = "1fr";
            cctvContainer.style.gap = "20px";

            if (shop.hlsUrls && shop.hlsUrls.length > 0) {
                cctvCountElement.textContent = `현재 등록된 CCTV 수 : ${shop.hlsUrls.length + 1}`;
                document.getElementById("noCCTVMessage").style.display = 'none';

                for (const [index, url] of shop.hlsUrls.entries()) {
                    const videoWrapper = document.createElement("div");
                    videoWrapper.style.position = "relative";
                    videoWrapper.style.width = "100%";
                    videoWrapper.style.paddingBottom = "56.25%"; // 16:9 aspect ratio
                    videoWrapper.style.height = "0";

                    const title = document.createElement("h4");
                    title.textContent = `CCTV ${index + 1}`;
                    title.style.position = "absolute";
                    title.style.top = "10px";
                    title.style.left = "10px";
                    title.style.color = "white";
                    title.style.backgroundColor = "rgba(0, 0, 0, 0.5)";
                    title.style.padding = "5px 10px";
                    title.style.margin = "0";
                    title.style.zIndex = "2";
                    videoWrapper.appendChild(title);

                    const deleteButton = document.createElement("button");
                    deleteButton.textContent = "삭제";
                    deleteButton.style.position = "absolute";
                    deleteButton.style.top = "-5px";
                    deleteButton.style.right = "10px";
                    deleteButton.style.color = "red";
                    deleteButton.style.border = "none";
                    deleteButton.style.padding = "5px 10px";
                    deleteButton.style.cursor = "pointer";
                    deleteButton.style.fontSize = "16px";
                    deleteButton.style.width = "auto";
                    deleteButton.style.backgroundColor = "white";
                    deleteButton.style.zIndex = "2";
                    deleteButton.onclick = async function() {
                        try {
                            const deleteResponse = await fetch(`/api/shops/${shopId}/cctv/${index}`, {
                                method: "DELETE",
                                headers: {
                                    "Authorization": `Bearer ${token}`
                                }
                            });
                            if (deleteResponse.ok) {
                                alert("CCTV deleted successfully");
                                window.location.reload();
                            } else {
                                const errorText = await deleteResponse.text();
                                alert(`Failed to delete CCTV: ${errorText || 'No additional information available'}`);
                            }
                        } catch (error) {
                            console.error("Error:", error);
                            alert("An error occurred while deleting the CCTV. Please check your network connection and try again.");
                        }
                    };

                    const video = document.createElement("video");
                    video.id = `video${index}`;
                    video.className = "video-js vjs-default-skin";
                    video.controls = true;
                    video.autoplay = true;
                    video.muted = true;
                    video.style.position = "absolute";
                    video.style.top = "0";
                    video.style.left = "0";
                    video.style.width = "100%";
                    video.style.height = "100%";
                    const source = document.createElement("source");
                    source.src = url; // HTTP 주소 사용
                    source.type = "application/x-mpegURL";
                    video.appendChild(source);

                    videoWrapper.appendChild(video);
                    videoWrapper.appendChild(deleteButton);
                    cctvContainer.appendChild(videoWrapper);

                    // Video.js 초기화
                    videojs(`video${index}`, {}, function() {
                        if (Hls.isSupported()) {
                            var hls = new Hls();
                            hls.loadSource(url);
                            hls.attachMedia(this);
                        } else if (this.canPlayType('application/vnd.apple.mpegurl')) {
                            this.src = url;
                        }
                        this.play();
                    });
                }
            } else {
                cctvCountElement.textContent = "현재 등록된 CCTV 수 : 0";
            }

            document.getElementById("viewDetectionButton").addEventListener("click", function() {
                window.location.href = `/detect.html?id=${shopId}`;
            });

        } else if (response.status === 403) {
            alert("You do not have permission to view this resource. Please login with the correct account.");
            window.location.href = "/login.html";
        } else {
            const errorText = await response.text();
            alert(`Failed to fetch shop details: ${errorText || 'No additional information available'}`);
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred while fetching shop details. Please check your network connection and try again.");
    }
});

async function fetchDetectionResults() {
    const params = new URLSearchParams(window.location.search);
    const shopId = params.get("id");
    const token = localStorage.getItem("token");

    if (!shopId || !token) {
        alert("No shop ID or token provided");
        return;
    }

    try {
        const cctvResponse = await fetch(`/api/shops/${shopId}/cctv/urls`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        });

        if (!cctvResponse.ok) {
            throw new Error('Failed to fetch CCTV URLs: ' + cctvResponse.statusText);
        }

        const videoUrls = await cctvResponse.json();
        const videoUrlsParam = videoUrls.join(',');

        const url = new URL('/predict', window.location.origin);
        url.search = new URLSearchParams({ video_urls: videoUrlsParam });

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const data = await response.json();
        displayResults(data.events);
    } catch (error) {
        console.error('Fetch error: ', error);
    }
}

function displayResults(events) {
    const resultsList = document.getElementById('resultsList');
    resultsList.innerHTML = ''; // Clear previous results

    if (events.length === 0) {
        resultsList.innerHTML = '<p>No anomalous behaviors detected.</p>';
        return;
    }

    events.forEach(event => {
        const eventElement = document.createElement('div');
        eventElement.innerHTML = `<p>CCTV ${event.cctv_index + 1} - Event: ${event.event}, Timestamp: ${event.timestamp}</p>`;
        resultsList.appendChild(eventElement);
    });
}

// 페이지 로드 시 결과를 가져옴
window.onload = fetchDetectionResults;

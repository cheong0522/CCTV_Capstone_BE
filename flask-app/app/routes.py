from . import app
from flask import jsonify, request, Response
from flask_cors import cross_origin
import cv2

model_path = "app/model/test_lstm.pt"

@app.route('/predict', methods=['POST'])
@cross_origin()
def predict_route():
    try:
        data = request.json
        if 'video_urls' not in data:
            print("Error: No video_urls provided")
            return jsonify({"error": "No video_urls provided"}), 400

        video_paths = data['video_urls']
        print(f"Received video paths: {video_paths}")
        events = predict_video(video_paths, model_path)
        print(f"Predicted events: {events}")
        return jsonify({"events": events})
    except Exception as e:
        print(f"Error during prediction: {e}")
        return jsonify({"error": str(e)}), 500

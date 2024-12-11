#!/bin/bash

DIRECTORY=${1:-.}

for file in "$DIRECTORY"/*.mp3; do
    if [[ -f "$file" ]]; then
        base_name=$(basename "$file" .mp3)
        ffmpeg -i "$file" "${DIRECTORY}/${base_name}.wav"
        echo "Konversi selesai: $file -> ${DIRECTORY}/${base_name}.wav"
    fi
done

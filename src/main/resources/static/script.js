function showFileName(event) {
  const file = event.target.files[0];
  const uploadText = document.getElementById('uploadText');
  const fileNameDiv = document.getElementById('fileName');

  if (file) {
    uploadText.textContent = "✅ File Selected:";
    fileNameDiv.textContent = file.name;
  } else {
    uploadText.textContent = "Choose File";
    fileNameDiv.textContent = "No file chosen";
  }
}

async function uploadAndScore() {
  const fileInput = document.getElementById('resumeFile');
  const rolesInput = document.getElementById('roles').value.trim();
  if (!fileInput.files[0] || !rolesInput) {
    alert('Please select a file and enter roles.');
    return;
  }

  const file = fileInput.files[0];
  const roles = rolesInput;

  try {
    // Step 1: Upload resume
    const formData = new FormData();
    formData.append("file", file);

    const uploadResponse = await fetch('http://localhost:8080/api/resume/upload', {
      method: 'POST',
      body: formData
    });

    if (!uploadResponse.ok) throw new Error('Upload failed');
    const resume = await uploadResponse.json();
    const resumeId = resume.id;

    // Step 2: Get multi-role scores
    const scoreResponse = await fetch(`http://localhost:8080/api/resume/score-multi/${resumeId}?roles=${encodeURIComponent(roles)}`);
    if (!scoreResponse.ok) throw new Error('Scoring failed');
    const scores = await scoreResponse.json();

    // Step 3: Display results
    const tbody = document.getElementById('resultsBody');
    tbody.innerHTML = '';

    for (const role in scores) {
      const row = document.createElement('tr');

      const tdRole = document.createElement('td');
      tdRole.textContent = role;
      row.appendChild(tdRole);

      const tdScore = document.createElement('td');
      tdScore.textContent = scores[role].score;
      row.appendChild(tdScore);

      const tdFeedback = document.createElement('td');
      const pre = document.createElement('pre');
      pre.textContent = scores[role].feedback;
      tdFeedback.appendChild(pre);
      row.appendChild(tdFeedback);

      tbody.appendChild(row);
    }

    document.getElementById('resultsTable').style.display = 'table';

  } catch (err) {
    console.error(err);
    alert('Error: ' + err.message);
  }
}

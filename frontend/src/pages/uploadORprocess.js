import { useState } from 'react';
import axios from 'axios';
import { useRouter } from 'next/router';

function HomePage() {
  const router = useRouter();
  const [selectedFile, setSelectedFile] = useState(null);

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]); // Sélectionnez le premier fichier de la liste
  };

  const handleFileUpload = async () => {
    if (selectedFile) {
      const formData = new FormData();
      formData.append('file', selectedFile);
      
      try {
        // Envoi du fichier vers le backend
        const response = await axios.post('http://localhost:8081/parseEmployees', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });

        // Si le fichier est téléchargé avec succès, rediriger vers la page /listEmploye
        if (response.status === 200) {
          // Extraire le chemin du fichier depuis la réponse
          const savedFilePath = response.data.filePath;

          // Rediriger vers la page /listEmploye avec le chemin du fichier en tant que paramètre
          router.push({
            pathname: '/listEmploye',
            query: { filePath: savedFilePath },
          });
        }
      } catch (error) {
        console.error('Error uploading file:', error);
        // Gérer les erreurs d'upload ici
      }
    }
  };

  const process = () => {
    router.push('/listEmploye');
  };

  return (
    <div>
      <input type="file" onChange={handleFileChange} />
      <button onClick={handleFileUpload}>Upload File</button>
      <button onClick={process}>Process</button>
    </div>
  );
}

export default HomePage;

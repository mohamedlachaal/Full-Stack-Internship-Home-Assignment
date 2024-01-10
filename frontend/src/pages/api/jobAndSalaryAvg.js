const apiUrl = 'http://localhost:8081/jobAndSalaryAvg';

export default async function handler(req, res) {
  const response = await fetch(apiUrl);
  const data = await response.json();

  res.status(200).json(data);
}
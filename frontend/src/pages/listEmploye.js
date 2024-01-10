// pages/index.js
import React from 'react';
import { useRouter } from 'next/router';
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
} from '@nextui-org/react';

function ListEmploye({ employees }) {
  const router = useRouter();
  const filePath = router.query.filePath;

  // Vérifier si les employés sont définis et s'ils sont un tableau
  if (!employees || !Array.isArray(employees)) {
    return <p>No data available</p>;
  }

  return (
    <Table>
      <TableHeader>
        <TableColumn>ID</TableColumn>
        <TableColumn>Name</TableColumn>
        <TableColumn>Job Title</TableColumn>
        <TableColumn>Salary</TableColumn>
      </TableHeader>
      <TableBody>
        {employees.map((employee) => (
          <TableRow key={employee.id}>
            <TableCell>{employee.id}</TableCell>
            <TableCell>{employee.name}</TableCell>
            <TableCell>{employee.jobTitle}</TableCell>
            <TableCell>{employee.salary}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}

export async function getServerSideProps() {
  try {
    const res = await fetch('http://localhost:8081/parseEmployees?file=filePath');
    const data = await res.json();

    return {
      props: {
        employees: data,
      },
    };
  } catch (error) {
    console.error('Error fetching data:', error);
    return {
      props: {
        employees: null,
      },
    };
  }
}

export default ListEmploye;

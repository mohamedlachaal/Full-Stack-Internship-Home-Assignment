// pages/index.js

import React from 'react';
import { Table, TableHeader, TableColumn, TableBody, TableRow, TableCell } from '@nextui-org/react';

function jobAndSalaryAvg({ jobTitleAVGs }) {
  return (
    <Table>
      <TableHeader>
        <TableColumn>Title</TableColumn>
        <TableColumn>Average Salary</TableColumn>
      </TableHeader>
      <TableBody>
        {jobTitleAVGs.map((job) => (
          <TableRow key={job.title}>
            <TableCell>{job.title}</TableCell>
            <TableCell>{job.avgSalary}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}

export async function getServerSideProps() {
  const res = await fetch('http://localhost:3000/api/jobAndSalaryAvg'); 
  const data = await res.json();

  return {
    props: {
      jobTitleAVGs: data , 
    },
  };
}

export default jobAndSalaryAvg;

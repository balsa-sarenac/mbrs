import React from 'react';
import 'antd/dist/antd.css';
import { Table } from 'antd';

const columns = [
<#list columns?keys as col>
  {
    title: '${columns[col]}',
    dataIndex: '${col}'
  },
</#list>  
];

const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(
      selectedRows
    );
  },
  getCheckboxProps: record => ({
    disabled: record.name === 'Disabled User',
    // Column configuration not to be checked
    name: record.name
  })
};

export const ${name} = () => {
  return (
    <div>
      <Table
        rowSelection={{
          type: 'radio',
          ...rowSelection
        }}
        columns={columns}
        data={[]}
      />
    </div>
  );
};

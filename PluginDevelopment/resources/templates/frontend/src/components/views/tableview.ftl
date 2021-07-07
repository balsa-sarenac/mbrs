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

export const ${name} = (props) => {
  return (
    <div>
      <Table
        rowSelection={{
          type: 'radio',
          ...props.rowSelection
        }}
        columns={columns}
        dataSource={props.data}
      />
    </div>
  );
};

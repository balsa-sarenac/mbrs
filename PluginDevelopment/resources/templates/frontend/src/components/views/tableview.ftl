import React, { useState } from 'react';
import 'antd/dist/antd.css';
import { Table, Button } from 'antd';
import { Viewer } from '../common/Viewer';



export const ${name}TableView = (props) => {
	const [isModalVisible, setIsModalVisible] = useState(false);
  	const [record, setRecord] = useState(null);
  	const [viewerName, setViewerName] = useState('');
  	
	const columns = [
	<#list elements as element>
	  {
	    title: '${element.columnName}',
	    dataIndex: '${element.sourceName}',
	    render : (text) => String(text),
	  },
	</#list>
	<#list referencedTypes as element>
	  {
	    title: '${element.columnName}',
	    dataIndex: '${element.sourceName}',
      	render: record => (<Button onClick={()=>{setViewerName('${element.columnName}'); setRecord(record); setIsModalVisible(true);}}>Details</Button>)
	  },
	</#list> 
	];
	
	const handleCancel = () => {
		setIsModalVisible(false);
	};
  return (
    <div>
      <Viewer data={record} isModalVisible={isModalVisible} name={viewerName} handleCancel={handleCancel}/>
      <Table
        rowSelection={{
          type: 'radio',
          ...props.rowSelection
        }}
        columns={columns}
        pagination={{hideOnSinglePage:true}}
        dataSource={props.data}
      />
    </div>
  );
};

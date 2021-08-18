import React from 'react';
import 'antd/dist/antd.css';
import { Modal, Table } from 'antd';


export const Viewer = (props) => {

  	if(props.data==null){
  		return(
  		<> 
  			<Modal title={props.name} visible={props.isModalVisible} footer={null} onCancel={props.handleCancel}>
		        <p>No content</p>
     		</Modal>
      	</>);
  	}else{
        let data = [];
        let columns = []
        if(Array.isArray(props.data)){
            data = props.data;
        }else{
            data.push(props.data);
        }
        for (let d of data){
            for(let col in d){
                columns.push({title:col, dataIndex:col, render : (text) => String(text),})
            }
        }
        return(
            <> 
  			<Modal title={props.name} visible={props.isModalVisible} footer={null} onCancel={props.handleCancel}>
              <Table
                columns={columns}
                pagination={{hideOnSinglePage:true}}
                dataSource={data}
            />
     		</Modal>
      	</>
        );
    }

};

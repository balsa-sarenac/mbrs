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
       let flag = false;
        for (let d of data){
            for(let col in d){
                if(col=='key'){
                    flag = true;
                    continue;
                }
                let col_str = col.charAt(0).toUpperCase() + col.substr(1).toLowerCase();
                columns.push({title:col_str, dataIndex:col, render : (text) => String(text),})
            }
            break;
        }
        if(!flag){
            let key=0;
            for(let d of data){
                d['key'] = key;
                key++;
            }
        }
        return(
            <> 
  			<Modal title={props.name} visible={props.isModalVisible} footer={null} onCancel={props.handleCancel} width={1000}>
              <Table
				scroll={{ x: true }}
                columns={columns}
                pagination={{hideOnSinglePage:true}}
                dataSource={data}
            />
     		</Modal>
      	</>
        );
    }

};

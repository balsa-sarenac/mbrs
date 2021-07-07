import React from 'react';
import { Row, Col, Typography } from 'antd';
const { Title } = Typography;

<#if formImport??>
import { ${formImport} } from './components/forms/${formImport}';
</#if>

<#if tableView??>
import { ${tableView} } from './components/views/${tableView}';
</#if>

export const ${name}Container = () => {
	
	return (
		<>
			<Row>
				<Col xs={{ span: 22, offset: 1 }} sm={{ span: 22, offset: 1 }} md={{ span: 22, offset: 1 }}>
					<Title>${name}</Title>
				</Col>
			</Row>
		</>
	)
};

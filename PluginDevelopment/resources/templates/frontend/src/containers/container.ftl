import React from 'react';
import { Row, Col, Typography } from 'antd';
<#if formImport??>
import { ${formImport} } from '../components/forms/${formImport}';
</#if>

<#if tableImport??>
import { ${tableImport} } from '../components/views/${tableImport}';
</#if>

const { Title } = Typography;

export const ${name}Container = () => {
	
	return (
		<>
			<Row>
				<Col xs={{ span: 22, offset: 1 }} sm={{ span: 22, offset: 1 }} md={{ span: 22, offset: 1 }}>
					<Title>${name}</Title>
				</Col>
			</Row>
			<#if tableImport??>
			<Row>
				<Col xs={{ span: 22, offset: 1 }} sm={{ span: 22, offset: 1 }} md={{ span: 22, offset: 1 }}>
					<${tableImport} />
				</Col>
			</Row>
			</#if>
		</>
	)
};

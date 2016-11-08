'use strict';

import React, {PropTypes} from 'react';
import {
	Image,
	NativeModules
} from 'react-native';

const QRCode = React.createClass({

	propTypes: {
		...Image.propTypes,
		content: React.PropTypes.string
	},

	getInitialState() {
		return {
			barcodeSource: null
		};
	},

	componentWillMount() {
		this.loadBarcode(this.props.content);
	},

	componentWillReceiveProps(nextProps) {
		if (!nextProps.content) {
			this.setState({
				barcodeSource: null
			});
		} else if (nextProps.content !== this.props.content) {
			this.loadBarcode(nextProps.content);
		}
	},

	loadBarcode(content) {
		NativeModules.RNBarcode.generateQrCode(content, 512).then(result => {
			this.setState({
				barcodeSource: {uri: `file:${result}`}
			});
		}).catch(err => {
			this.setState({
				barcodeSource: null
			});
		});
	},

	render() {
		return (
			<Image {...this.props} resizeMode={"contain"} source={this.state.barcodeSource} />
		);
	}
});

export default QRCode;

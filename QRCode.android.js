'use strict';

import React from 'react';
import {
	Image,
	NativeModules
} from 'react-native';

import PropTypes from "prop-types";
import createReactClass from "create-react-class";

const QRCode = createReactClass({

	propTypes: {
		...Image.propTypes,
		content: PropTypes.string
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

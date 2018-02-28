/**
 * @providesModule QRCode
 * @flow
 */

'use strict';

import React from 'react';
import PropTypes from "prop-types";

import {
  requireNativeComponent,
} from 'react-native';

class QRCode extends React.Component {
  render() {
    return <LFQRCode {...this.props} />;
  }
}

QRCode.propTypes = {
  /**
   * This is the content of the generated Aztec Code
   */
   content: PropTypes.string,
};

var LFQRCode = requireNativeComponent('LFQRCode', QRCode);

module.exports = QRCode;

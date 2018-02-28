/**
 * @providesModule AztecCode
 * @flow
 */

'use strict';

import React from 'react';
import PropTypes from "prop-types";

import {
  requireNativeComponent,
} from 'react-native';

class AztecCode extends React.Component {
  render() {
    return <LFAztecCode {...this.props} />;
  }
}

AztecCode.propTypes = {
  /**
   * This is the content of the generated Aztec Code
   */
   content: PropTypes.string,
};

var LFAztecCode = requireNativeComponent('LFAztecCode', AztecCode);

module.exports = AztecCode;

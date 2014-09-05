/**
 * VNodeInfoIaaS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package bupt.sc.nova.statistic;

public class VNodeInfoIaaS  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String createTime;

    private java.lang.String hm_ip;

    private java.lang.String ipAddr;

    private java.lang.String nodeId;

    private java.lang.String nodeName;

    private java.lang.String nodeType;

    private java.lang.String state;

    private int subnetId;

    public VNodeInfoIaaS() {
    }

    public VNodeInfoIaaS(
           java.lang.String createTime,
           java.lang.String hm_ip,
           java.lang.String ipAddr,
           java.lang.String nodeId,
           java.lang.String nodeName,
           java.lang.String nodeType,
           java.lang.String state,
           int subnetId) {
           this.createTime = createTime;
           this.hm_ip = hm_ip;
           this.ipAddr = ipAddr;
           this.nodeId = nodeId;
           this.nodeName = nodeName;
           this.nodeType = nodeType;
           this.state = state;
           this.subnetId = subnetId;
    }


    /**
     * Gets the createTime value for this VNodeInfoIaaS.
     * 
     * @return createTime
     */
    public java.lang.String getCreateTime() {
        return createTime;
    }


    /**
     * Sets the createTime value for this VNodeInfoIaaS.
     * 
     * @param createTime
     */
    public void setCreateTime(java.lang.String createTime) {
        this.createTime = createTime;
    }


    /**
     * Gets the hm_ip value for this VNodeInfoIaaS.
     * 
     * @return hm_ip
     */
    public java.lang.String getHm_ip() {
        return hm_ip;
    }


    /**
     * Sets the hm_ip value for this VNodeInfoIaaS.
     * 
     * @param hm_ip
     */
    public void setHm_ip(java.lang.String hm_ip) {
        this.hm_ip = hm_ip;
    }


    /**
     * Gets the ipAddr value for this VNodeInfoIaaS.
     * 
     * @return ipAddr
     */
    public java.lang.String getIpAddr() {
        return ipAddr;
    }


    /**
     * Sets the ipAddr value for this VNodeInfoIaaS.
     * 
     * @param ipAddr
     */
    public void setIpAddr(java.lang.String ipAddr) {
        this.ipAddr = ipAddr;
    }


    /**
     * Gets the nodeId value for this VNodeInfoIaaS.
     * 
     * @return nodeId
     */
    public java.lang.String getNodeId() {
        return nodeId;
    }


    /**
     * Sets the nodeId value for this VNodeInfoIaaS.
     * 
     * @param nodeId
     */
    public void setNodeId(java.lang.String nodeId) {
        this.nodeId = nodeId;
    }


    /**
     * Gets the nodeName value for this VNodeInfoIaaS.
     * 
     * @return nodeName
     */
    public java.lang.String getNodeName() {
        return nodeName;
    }


    /**
     * Sets the nodeName value for this VNodeInfoIaaS.
     * 
     * @param nodeName
     */
    public void setNodeName(java.lang.String nodeName) {
        this.nodeName = nodeName;
    }


    /**
     * Gets the nodeType value for this VNodeInfoIaaS.
     * 
     * @return nodeType
     */
    public java.lang.String getNodeType() {
        return nodeType;
    }


    /**
     * Sets the nodeType value for this VNodeInfoIaaS.
     * 
     * @param nodeType
     */
    public void setNodeType(java.lang.String nodeType) {
        this.nodeType = nodeType;
    }


    /**
     * Gets the state value for this VNodeInfoIaaS.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this VNodeInfoIaaS.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the subnetId value for this VNodeInfoIaaS.
     * 
     * @return subnetId
     */
    public int getSubnetId() {
        return subnetId;
    }


    /**
     * Sets the subnetId value for this VNodeInfoIaaS.
     * 
     * @param subnetId
     */
    public void setSubnetId(int subnetId) {
        this.subnetId = subnetId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VNodeInfoIaaS)) return false;
        VNodeInfoIaaS other = (VNodeInfoIaaS) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.createTime==null && other.getCreateTime()==null) || 
             (this.createTime!=null &&
              this.createTime.equals(other.getCreateTime()))) &&
            ((this.hm_ip==null && other.getHm_ip()==null) || 
             (this.hm_ip!=null &&
              this.hm_ip.equals(other.getHm_ip()))) &&
            ((this.ipAddr==null && other.getIpAddr()==null) || 
             (this.ipAddr!=null &&
              this.ipAddr.equals(other.getIpAddr()))) &&
            ((this.nodeId==null && other.getNodeId()==null) || 
             (this.nodeId!=null &&
              this.nodeId.equals(other.getNodeId()))) &&
            ((this.nodeName==null && other.getNodeName()==null) || 
             (this.nodeName!=null &&
              this.nodeName.equals(other.getNodeName()))) &&
            ((this.nodeType==null && other.getNodeType()==null) || 
             (this.nodeType!=null &&
              this.nodeType.equals(other.getNodeType()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            this.subnetId == other.getSubnetId();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCreateTime() != null) {
            _hashCode += getCreateTime().hashCode();
        }
        if (getHm_ip() != null) {
            _hashCode += getHm_ip().hashCode();
        }
        if (getIpAddr() != null) {
            _hashCode += getIpAddr().hashCode();
        }
        if (getNodeId() != null) {
            _hashCode += getNodeId().hashCode();
        }
        if (getNodeName() != null) {
            _hashCode += getNodeName().hashCode();
        }
        if (getNodeType() != null) {
            _hashCode += getNodeType().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        _hashCode += getSubnetId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VNodeInfoIaaS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnapi.control.pl.nos.bupt", "VNodeInfoIaaS"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hm_ip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hm_ip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipAddr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipAddr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nodeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nodeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nodeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nodeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subnetId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subnetId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

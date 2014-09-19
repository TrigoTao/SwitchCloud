package bupt.sc.nova.statistic;

/**
 * VMInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */


public class VMInfo  implements java.io.Serializable {
    private java.lang.String ipAddress;

    private java.lang.String hostName;

    private java.lang.String macAddress;

    private int memoryInM;

    private int diskInG;

    private int cpu;

    private int currentDiskInG;

    private int currentMemInG;

    private java.lang.String type;

    private java.lang.String size;

    private java.lang.String vmstatus;

    public VMInfo() {
    }

    public VMInfo(
           java.lang.String ipAddress,
           java.lang.String hostName,
           java.lang.String macAddress,
           int memoryInM,
           int diskInG,
           int cpu,
           int currentDiskInG,
           int currentMemInG,
           java.lang.String type,
           java.lang.String size,
           java.lang.String vmstatus) {
           this.ipAddress = ipAddress;
           this.hostName = hostName;
           this.macAddress = macAddress;
           this.memoryInM = memoryInM;
           this.diskInG = diskInG;
           this.cpu = cpu;
           this.currentDiskInG = currentDiskInG;
           this.currentMemInG = currentMemInG;
           this.type = type;
           this.size = size;
           this.vmstatus = vmstatus;
    }


    /**
     * Gets the ipAddress value for this VMInfo.
     * 
     * @return ipAddress
     */
    public java.lang.String getIpAddress() {
        return ipAddress;
    }


    /**
     * Sets the ipAddress value for this VMInfo.
     * 
     * @param ipAddress
     */
    public void setIpAddress(java.lang.String ipAddress) {
        this.ipAddress = ipAddress;
    }


    /**
     * Gets the hostName value for this VMInfo.
     * 
     * @return hostName
     */
    public java.lang.String getHostName() {
        return hostName;
    }


    /**
     * Sets the hostName value for this VMInfo.
     * 
     * @param hostName
     */
    public void setHostName(java.lang.String hostName) {
        this.hostName = hostName;
    }


    /**
     * Gets the macAddress value for this VMInfo.
     * 
     * @return macAddress
     */
    public java.lang.String getMacAddress() {
        return macAddress;
    }


    /**
     * Sets the macAddress value for this VMInfo.
     * 
     * @param macAddress
     */
    public void setMacAddress(java.lang.String macAddress) {
        this.macAddress = macAddress;
    }


    /**
     * Gets the memoryInM value for this VMInfo.
     * 
     * @return memoryInM
     */
    public int getMemoryInM() {
        return memoryInM;
    }


    /**
     * Sets the memoryInM value for this VMInfo.
     * 
     * @param memoryInM
     */
    public void setMemoryInM(int memoryInM) {
        this.memoryInM = memoryInM;
    }


    /**
     * Gets the diskInG value for this VMInfo.
     * 
     * @return diskInG
     */
    public int getDiskInG() {
        return diskInG;
    }


    /**
     * Sets the diskInG value for this VMInfo.
     * 
     * @param diskInG
     */
    public void setDiskInG(int diskInG) {
        this.diskInG = diskInG;
    }


    /**
     * Gets the cpu value for this VMInfo.
     * 
     * @return cpu
     */
    public int getCpu() {
        return cpu;
    }


    /**
     * Sets the cpu value for this VMInfo.
     * 
     * @param cpu
     */
    public void setCpu(int cpu) {
        this.cpu = cpu;
    }


    /**
     * Gets the currentDiskInG value for this VMInfo.
     * 
     * @return currentDiskInG
     */
    public int getCurrentDiskInG() {
        return currentDiskInG;
    }


    /**
     * Sets the currentDiskInG value for this VMInfo.
     * 
     * @param currentDiskInG
     */
    public void setCurrentDiskInG(int currentDiskInG) {
        this.currentDiskInG = currentDiskInG;
    }


    /**
     * Gets the currentMemInG value for this VMInfo.
     * 
     * @return currentMemInG
     */
    public int getCurrentMemInG() {
        return currentMemInG;
    }


    /**
     * Sets the currentMemInG value for this VMInfo.
     * 
     * @param currentMemInG
     */
    public void setCurrentMemInG(int currentMemInG) {
        this.currentMemInG = currentMemInG;
    }


    /**
     * Gets the type value for this VMInfo.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this VMInfo.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the size value for this VMInfo.
     * 
     * @return size
     */
    public java.lang.String getSize() {
        return size;
    }


    /**
     * Sets the size value for this VMInfo.
     * 
     * @param size
     */
    public void setSize(java.lang.String size) {
        this.size = size;
    }


    /**
     * Gets the vmstatus value for this VMInfo.
     * 
     * @return vmstatus
     */
    public java.lang.String getVmstatus() {
        return vmstatus;
    }


    /**
     * Sets the vmstatus value for this VMInfo.
     * 
     * @param vmstatus
     */
    public void setVmstatus(java.lang.String vmstatus) {
        this.vmstatus = vmstatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VMInfo)) return false;
        VMInfo other = (VMInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ipAddress==null && other.getIpAddress()==null) || 
             (this.ipAddress!=null &&
              this.ipAddress.equals(other.getIpAddress()))) &&
            ((this.hostName==null && other.getHostName()==null) || 
             (this.hostName!=null &&
              this.hostName.equals(other.getHostName()))) &&
            ((this.macAddress==null && other.getMacAddress()==null) || 
             (this.macAddress!=null &&
              this.macAddress.equals(other.getMacAddress()))) &&
            this.memoryInM == other.getMemoryInM() &&
            this.diskInG == other.getDiskInG() &&
            this.cpu == other.getCpu() &&
            this.currentDiskInG == other.getCurrentDiskInG() &&
            this.currentMemInG == other.getCurrentMemInG() &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.size==null && other.getSize()==null) || 
             (this.size!=null &&
              this.size.equals(other.getSize()))) &&
            ((this.vmstatus==null && other.getVmstatus()==null) || 
             (this.vmstatus!=null &&
              this.vmstatus.equals(other.getVmstatus())));
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
        if (getIpAddress() != null) {
            _hashCode += getIpAddress().hashCode();
        }
        if (getHostName() != null) {
            _hashCode += getHostName().hashCode();
        }
        if (getMacAddress() != null) {
            _hashCode += getMacAddress().hashCode();
        }
        _hashCode += getMemoryInM();
        _hashCode += getDiskInG();
        _hashCode += getCpu();
        _hashCode += getCurrentDiskInG();
        _hashCode += getCurrentMemInG();
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getSize() != null) {
            _hashCode += getSize().hashCode();
        }
        if (getVmstatus() != null) {
            _hashCode += getVmstatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VMInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://capi.control.pl.nos.bupt", "VMInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ipAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "hostName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("macAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "macAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memoryInM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "memoryInM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("diskInG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "diskInG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cpu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cpu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentDiskInG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentDiskInG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currentMemInG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currentMemInG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("size");
        elemField.setXmlName(new javax.xml.namespace.QName("", "size"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vmstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vmstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
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

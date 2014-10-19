USE SwitchCloud;
delete from VNodeInfo;
delete from VNNodeInfo;
update VRDEPort set status = 'free';
update IpInfo set status = 'free';
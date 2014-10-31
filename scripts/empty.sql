USE SwitchCloud;
delete from VNodeInfo;
delete from VNNodeInfo;
delete from SubnetInfo;
update VRDEPort set status = 'free';
update IpInfo set status = 'free';
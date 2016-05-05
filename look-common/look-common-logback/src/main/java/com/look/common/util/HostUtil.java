package com.look.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * user: xingjun.zhang
 * Date: 2015/8/5
 */
public class HostUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HostUtil.class);
    private static final String LOCALHOST = "127.0.0.1";
    private static final String ANYHOST = "0.0.0.0";
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static String localHost = null;
    private static String localIp = null;


    private static InetAddress getLocalAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Exception e) {
                                    LOGGER.warn("Failed to retriving ip address, " + e.getMessage(), e);
                                }
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Failed to retriving ip address, " + e.getMessage(), e);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }
        LOGGER.error("Could not get local host ip address, will use 127.0.0.1 instead.");
        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress())
            return false;
        String name = address.getHostAddress();
        return (name != null
                && !ANYHOST.equals(name)
                && !LOCALHOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

    public static String getLocalHost() {
        if (localHost == null) {
            InetAddress address = getLocalAddress();
            localHost = address == null ? LOCALHOST : address.getHostName();
        }
        return localHost;
    }

    public static String getLocalIp() {
        if (localIp == null) {
            localIp = getIpByHost(getLocalHost());
        }
        return localIp;
    }

    public static String getIpByHost(String hostName) {
        try {
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (UnknownHostException e) {
            return hostName;
        }
    }
}

package com.example.portfolio.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

@RestController
public class DiagnosticController {

    private static final String HOST =
            "aws-0-ap-southeast-1.pooler.supabase.com";

    @GetMapping("/diagnostic/db-network")
    public String testConnection() {

        StringBuilder result = new StringBuilder();

        // 1. DNS
        try {
            InetAddress[] addresses = InetAddress.getAllByName(HOST);

            result.append("DNS: SUCCESS\n");

            for (InetAddress address : addresses) {
                result.append("IP: ")
                        .append(address.getHostAddress())
                        .append("\n");
            }

        } catch (Exception e) {
            result.append("DNS: FAILED\n");
            result.append(e).append("\n");
            return result.toString();
        }

        // 2. TCP
        try (Socket socket = new Socket()) {

            socket.connect(
                    new InetSocketAddress(HOST, 5432),
                    5000
            );

            result.append("TCP 5432: SUCCESS\n");

        } catch (Exception e) {
            result.append("TCP 5432: FAILED\n");
            result.append(e).append("\n");
            return result.toString();
        }

        return result.toString();
    }
}
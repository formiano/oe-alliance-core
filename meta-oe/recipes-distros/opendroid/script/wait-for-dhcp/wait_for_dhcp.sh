#!/bin/sh

log_file="/home/root/logs/network.log"
mkdir -p "$(dirname "$log_file")"
echo "" > "$log_file"

log_message() {
    echo "$(date '+%Y-%m-%d %H:%M:%S'): $1" >> "$log_file"
}

check_ipv4() {
    ifconfig "$1" 2>/dev/null | awk '/inet addr:/ {gsub("addr:", "", $2); print $2}' | head -n 1
}

check_ipv6() {
    ifconfig "$1" 2>/dev/null | awk '/inet6 addr:/ {print $3}' | head -n 1
}

wait_for_seconds() {
    sleep "$1"
}

log_message "Starting network check..."

for iface in eth0 eth1 wlan0 wlan3; do
    if grep -Eq "^[[:space:]]*auto[[:space:]]+$iface([[:space:]]|$)" /etc/network/interfaces; then
        if grep -Eqi "^[[:space:]]*iface[[:space:]]+$iface[[:space:]]+inet[[:space:]]+dhcp([[:space:]]|$)" /etc/network/interfaces || \
           grep -Eqi "^[[:space:]]*iface[[:space:]]+$iface[[:space:]]+inet6[[:space:]]+dhcp([[:space:]]|$)" /etc/network/interfaces; then

            log_message "Interface $iface configured for DHCP.."

            ATTEMPTS=0
            MAX_ATTEMPTS=20

            while [ "$ATTEMPTS" -lt "$MAX_ATTEMPTS" ]; do
                IPV4=$(check_ipv4 "$iface")
                IPV6=$(check_ipv6 "$iface")

                if grep -qi "^[[:space:]]*iface[[:space:]]\+$iface[[:space:]]\+inet[[:space:]]\+dhcp" /etc/network/interfaces; then
                    if [ -n "$IPV4" ]; then
                        log_message "IPv4 DHCP address obtained for $iface - IPv4: $IPV4"
                        break
                    fi
                elif grep -qi "^[[:space:]]*iface[[:space:]]\+$iface[[:space:]]\+inet6[[:space:]]\+dhcp" /etc/network/interfaces; then
                    GLOBAL_IPV6=$(printf '%s\n' "$IPV6" | grep -v '^fe80:')

                    if [ -n "$GLOBAL_IPV6" ]; then
                        log_message "IPv6 DHCP address obtained for $iface - IPv6: $GLOBAL_IPV6"
                        break
                    fi
                fi

                log_message "$ATTEMPTS: No IP yet for $iface, retrying..."
                wait_for_seconds 1
                ATTEMPTS=$((ATTEMPTS + 1))
            done

            if [ "$ATTEMPTS" -eq "$MAX_ATTEMPTS" ]; then
                log_message "Max attempts reached. No IP for $iface. Exiting."
                exit 1
            fi

            break
        fi
    fi
done

log_message "Contents of /etc/resolv.conf:"
cat /etc/resolv.conf >> "$log_file"

log_message "Check DNS (ping to google.com)..."
if ping -c 1 -W 2 google.com > /dev/null 2>&1; then
    log_message "DNS working (google.com reachable)."
else
    log_message "DNS not working (google.com unreachable)."
fi

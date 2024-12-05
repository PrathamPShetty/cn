import java.io.*;

class Crc {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] data, div, divisor, rem, crc;
        int data_bits, divisor_bits, tot_length;

        // Input data bits
        System.out.print("Enter number of data bits: ");
        data_bits = Integer.parseInt(br.readLine());
        data = new int[data_bits];
        System.out.println("Enter data bits:");
        for (int i = 0; i < data_bits; i++) {
            data[i] = Integer.parseInt(br.readLine());
        }

        // Input divisor bits
        System.out.print("Enter number of bits in divisor: ");
        divisor_bits = Integer.parseInt(br.readLine());
        divisor = new int[divisor_bits];
        System.out.println("Enter divisor bits:");
        for (int i = 0; i < divisor_bits; i++) {
            divisor[i] = Integer.parseInt(br.readLine());
        }

        // Compute CRC
        tot_length = data_bits + divisor_bits - 1;
        div = new int[tot_length];
        rem = new int[tot_length];
        crc = new int[tot_length];

        // Append 0's to data
        System.arraycopy(data, 0, div, 0, data_bits);
        System.out.print("Dividend (after appending 0's): ");
        for (int i : div) System.out.print(i);
        System.out.println();

        // Perform division
        System.arraycopy(div, 0, rem, 0, div.length);
        rem = divide(div, divisor, rem);

        // Generate CRC code
        for (int i = 0; i < div.length; i++) {
            crc[i] = div[i] ^ rem[i];
        }

        // Display CRC
        System.out.print("CRC code: ");
        for (int i : crc) System.out.print(i);
        System.out.println();

        // Error detection
        System.out.println("Enter received CRC code (" + tot_length + " bits): ");
        for (int i = 0; i < crc.length; i++) {
            crc[i] = Integer.parseInt(br.readLine());
        }
        System.arraycopy(crc, 0, rem, 0, crc.length);
        rem = divide(crc, divisor, rem);

        // Check remainder for errors
        boolean error = false;
        for (int bit : rem) {
            if (bit != 0) {
                error = true;
                break;
            }
        }

        // Output result
        System.out.println(error ? "Error detected" : "No Error");
        System.out.println("THANK YOU .......");
    }

    // Division logic for binary division using XOR
    static int[] divide(int div[], int divisor[], int rem[]) {
        int cur = 0;
        while ((rem.length - cur) >= divisor.length) {
            for (int i = 0; i < divisor.length; i++) {
                rem[cur + i] ^= divisor[i];
            }
            while (cur < rem.length && rem[cur] == 0) cur++;
        }
        return rem;
    }
}

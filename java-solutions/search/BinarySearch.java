package search;

public class BinarySearch {
    // Pred: int x; int[] a; ∀ int i ∈ [0; args.length - 2] a[i] >= a[i + 1]; let a[a.len] = -inf
    // Post: result ∈ [0; args.length - 1], a[result] <= x < a[result - 1]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // int x = int(args[0])
        int[] a = new int[args.length - 1];
        // int[] a = new int[args.length - 1]
        // I: i ∈ [0, args.length - 1]
        for (int i = 0; i < args.length - 1; i++) {
            // i' = i' + 1
            a[i] = Integer.parseInt(args[i + 1]);
            // a[i'] = args[i' + 1]
        }
        // a = list(map(int, args[1:]))
//        System.out.println(RecursiveBinarySearch(-1, a.length, x, a));
        System.out.println(IterativeBinarySearch(x, a));

    }

    // Pred: -1 <= left < right <= len(a) && int x; a[i] >= a[i + 1] ∀i ∈ [0; a.length] && let a[a.length] := Integer.MinValue
    // Post: res = min(∀right' ∈ [0, len(a)], таких что a[right'] <= x)
    private static int IterativeBinarySearch(final int x, final int[] a) {
        int left = -1;
        // left = -1
        int right = a.length;
        // right = a.length
        // I:  -1 <= left < right <= len(a)
        while (left + 1 < right) {
            // I && left + 1 < right
            int midl = left + (right - left) / 2;
            // right - (right - left) / 2 = (left + right) / 2 = left + (right - left) / 2
            // (left + right) / 2 > left тк (right - left) > 0 -> (right - left) / 2 > 0
            // (left + right) / 2 < right тк (right - left) > 0 -> (right - left) / 2 > 0
            // midl' ∈ (left, right)
            if (a[midl] <= x) {
                // a[midl'] <= x && I
                right = midl;
                // a[left'] > x
                // I
            } else {
                // a[midl'] > x && I
                left = midl;
                // a[left'] > x
                // I
            }
            // I
        }
        // -1 <= left' < right' <= len(a)
        // тк left' == right' - 1 ->
        //  0 <= right' <= len(a)
        // right = min(∀right' ∈ [0, len(a) - 1], таких что a[right'] <= x)
        return right;
        // -> Post
    }

    // Pred: -1 <= left < right <= len(a) && int x; a[i] >= a[i + 1] ∀i ∈ [0; a.length - 2] && a[a.length] := Integer.MinValue
    // Post: res = min(∀right' ∈ [0, len(a)], таких что a[right'] <= x)
    private static int RecursiveBinarySearch(int left, int right, final int x, final int[] a) {
        // Pred
        if (left + 1 >= right) {
            // -1 <= left' < right' <= len(a)
            // тк left' == right' - 1
            // 0 <= right' <= len(a)
            // right = min(∀right' ∈ [0, len(a) - 1], таких что a[right'] <= x)
            return right;
            // -> Post
        }
        int midl = left + (right - left) / 2;
        // right - (right - left) / 2 = (left + right) / 2 = left + (right - left) / 2
        // (left + right) / 2 > left тк (right - left) > 0 -> (right - left) / 2 > 0
        // (left + right) / 2 < right тк (right - left) > 0 -> (right - left) / 2 > 0
        // midl' ∈ (left, right)
        if (a[midl] <= x) {
            // a[midl'] <= x
            right = midl;
            // a[right'] <= x -> Pred
            return RecursiveBinarySearch(left, right, x, a);
            // -> Post
        } else {
            // a[midl'] > x
            left = midl;
            // // a[left'] > x -> Pred
            return RecursiveBinarySearch(left, right, x, a);
            // -> Post
        }
    }
}
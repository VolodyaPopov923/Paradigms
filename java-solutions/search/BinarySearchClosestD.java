package search;

public class BinarySearchClosestD {
    // Pred: int x; int[] a; ∀ int i ∈ [0; args.length - 2] a[i] >= a[i + 1]
    // Post: ∀result ∈ [0; args.length - 1], a[result] <= x < a[result - 1]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // int x = int(args[0])
        int[] a = new int[args.length - 1];
        // int[] a = new int[args.length - 1]
        int cnt = 0;
        //  int cnt = 0
        // I: i ∈ [0, args.length - 1]
        for (int i = 0; i < args.length - 1; i++){
            // i' = i' + 1
            a[i] = Integer.parseInt(args[i + 1]);
            // a[i'] = args[i' + 1]
            cnt += a[i];
            // cnt' = cnt' + a[i']
        }
        // cnt = sum(args[1:])
        // a = list(map(int, args[1:]))
        if (cnt % 2 == 0) {
            // cnt % 2 == 0
            System.out.println(recursiveBinarySearch(-1, a.length, x, a));
        } else{
            // cnt % 2 == 1
            System.out.println(iterativeBinarySearch(x, a));
        }
    }

    // Pred: -1 <= left < right <= len(a) && int x; a[i] >= a[i + 1] ∀i ∈ [0; a.length - 2] && a[a.length] == Integer.MinValue
    // Post: min(∀result ∈ [0; args.length])
    private static int iterativeBinarySearch(final int x, final int[] a){
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
            if (a[midl] <= x){
                // a[midl'] <= x && I
                right = midl;
                // a[left'] > x
                // I
            } else{
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
        return a[right - checkDelta(right, x, a)];
        // -> Post
    }

    // Pred: -1 <= left < right <= len(a) && int x; a[i] >= a[i + 1] ∀i ∈ [0; a.length - 2] && a[a.length] == Integer.MinValue
    // Post: res = min(∀i ∈ [0; args.length - 2] таких что )
    private static int recursiveBinarySearch(int left, int right, final int x, final int[] a){
        // Pred
        if (left + 1 >= right){
            // -1 <= left' < right' <= len(a)
            // тк left' == right' - 1
            // 0 <= right' <= len(a)
            // right = min(∀right' ∈ [0, len(a) - 1], таких что a[right'] <= x)
            return a[right - checkDelta(right, x, a)];
            // -> Post
        }
        int midl = left + (right - left) / 2;
        // right - (right - left) / 2 = (left + right) / 2 = left + (right - left) / 2
        // (left + right) / 2 > left тк (right - left) > 0 -> (right - left) / 2 > 0
        // (left + right) / 2 < right тк (right - left) > 0 -> (right - left) / 2 > 0
        // midl' ∈ (left, right)
        if (a[midl] <= x){
            // a[midl'] <= x
            right = midl;
            // a[right'] <= x -> Pred
            return recursiveBinarySearch(left, right, x, a);
            // -> Post
        } else {
            // a[midl'] > x
            left = midl;
            // // a[left'] > x -> Pred
            return recursiveBinarySearch(left, right, x, a);
            // -> Post
        }
    }

    //pred: 0 <= right <= len(a) && int x; a[i] >= a[i + 1] ∀i ∈ [0; a.length] && a[a.length] == Integer.MinValue
    //post: 0 <= right - res <= len(a)
    private static int checkDelta(int right, int x, int[] a){
        if (right == a.length || (right != 0 && Math.abs(x - a[right]) >= Math.abs(x - a[right - 1]))){
            // 0 < right <= len(a)
            // 0 <= right - res <= len(a)
            return 1;
        }
        // 0 <= right - res <= len(a)
        return 0;
    }
}
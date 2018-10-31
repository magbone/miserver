package server.response.parser.util;




public class SyntaxTree{

    private TreeNode root;

    private int count;

    public SyntaxTree(){
        count = 0;
    }
    public void addChild(Object o){
        if (count == 0) {
            root = new TreeNode(o);
            count ++;
            return;
        }


    }

    public void addBrother(Object o){
        if (count == 0) {
            root = new TreeNode(o);
            count ++;
            return;
        }
    }
    class TreeNode {
        private Object o; //data
        private TreeNode firstChild; //left child;
        private TreeNode nextSibling; //right brother

        public TreeNode(){
            this(null);
        }
        public TreeNode(Object o){
            this(o,null,null);
        }
        public TreeNode(Object o,TreeNode firstChild,TreeNode nextSibling){
            this.o = o;
            this.firstChild = firstChild;
            this.nextSibling = nextSibling;
        }

        public void setO(Object o) {
            this.o = o;
        }

        public void setFirstChild(TreeNode firstChild) {
            this.firstChild = firstChild;
        }

        public void setNextSibling(TreeNode nextSibling) {
            this.nextSibling = nextSibling;
        }

        public TreeNode getFirstChild() {
            return firstChild;
        }

        public TreeNode getNextSibling() {
            return nextSibling;
        }

        public Object getO() {
            return o;
        }
    }
}

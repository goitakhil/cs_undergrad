
public class EightPuzzleState extends State
{
    String board;
    int blank;

    Action actionList[];

    private Action[] allocActions()
    {
        switch(blank)
        {
            case 0:
                Action[] a = { new UpMove(), new LeftMove() };
                return a;
            case 1:
                Action[] b = { new UpMove(), new LeftMove(), new RightMove() };
                return b;
            case 2:
                Action[] c = { new UpMove(), new RightMove() };
                return c;
            case 3:
                Action[] d = { new UpMove(), new LeftMove(), new DownMove() };
                return d;
            case 4:
                Action[] e = { new RightMove(), new UpMove(), new LeftMove(), new DownMove() };
                return e;
            case 5:
                Action[] f = { new UpMove(), new RightMove(), new DownMove() };
                return f;
            case 6:
                Action[] g = { new LeftMove(), new DownMove() };
                return g;
            case 7:
                Action[] h = { new RightMove(), new LeftMove(), new DownMove() };
                return h;
            default:
                Action[] i = { new RightMove(), new DownMove() };
                return i;
        }
    }

    public EightPuzzleState(String board, EightPuzzleState parent)
    {
        super(parent);
        this.board=board;
        blank = board.indexOf(' ');
        actionList = allocActions();
    }

    public EightPuzzleState(String board)
    {
        super();
        this.board=board;
        blank = board.indexOf(' ');
        actionList = allocActions();
    }

    public Action[] getAvailableActions()
    {
       return actionList;
    }
    public boolean isGoal()
    {
        return "12345678 ".equals(board);
    }
    public boolean canActOn()
    {
        return true;
    }

    public String toString()
    {
        return board.substring(0,3) + "\n" + board.substring(3,6) + "\n" + board.substring(6) + "\n\n";
    }

    public int hashCode()
    {
        return board.hashCode();
    }

    public boolean equals(Object s)
    {
        if(!(s instanceof EightPuzzleState)) return false;
        return board.equals(((EightPuzzleState)(s)).board);
    }

    public class DownMove implements Action<EightPuzzleState>
    {
        /** returns a new state based on the given action */
        public EightPuzzleState updateState()
        {
            return new EightPuzzleState(board.substring(0,blank-3) + ' ' + board.substring(blank-2,blank) + board.charAt(blank-3) + board.substring(blank+1),EightPuzzleState.this);
        }

        public String toString() { return "DownMove"; }
    }

    public class LeftMove implements Action<EightPuzzleState>
    {
        /** returns a new state based on the given action */
        public EightPuzzleState updateState()
        {
            return new EightPuzzleState(board.substring(0,blank) + board.charAt(blank+1) + ' ' + board.substring(blank+2), EightPuzzleState.this);
        }
        public String toString() { return "LeftMove"; }
    }

    public class RightMove implements Action<EightPuzzleState>
    {
        /** returns a new state based on the given action */
        public EightPuzzleState updateState()
        {
            return new EightPuzzleState(board.substring(0,blank-1) + ' ' + board.charAt(blank-1) + board.substring(blank+1),EightPuzzleState.this);
        }
        public String toString() { return "RightMove"; }
    }

    public class UpMove implements Action<EightPuzzleState>
    {
        /** returns a new state based on the given action */
        public EightPuzzleState updateState()
        {
            return new EightPuzzleState(board.substring(0,blank) + board.charAt(blank+3) + board.substring(blank+1,blank+3) + ' ' + board.substring(blank+4),EightPuzzleState.this);
        }
        public String toString() { return "UpMove"; }
    }



}


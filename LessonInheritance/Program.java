public class Program {

   static public void main(String[] args) {

        InheritedClassWithMembers inherit = new InheritedClassWithMembers();

        inherit.anyoneCanSee = 5;

        inherit.onlyInheritedClassCanSee = 10;

        //inherit.onlyThisClassCanSee = 15;  

   }

}

import scala.actors._
import scala.actors.Actor._
import scala.actors.remote._
import scala.actors.remote.RemoteActor._

object Actor1 extends Actor {
  def act = {
               alive(9010)
	        register('myActor1, self)
		val printActor = RemoteActor.select(Node("128.227.248.230", 9010),'myActor_print)	
		println("worker started")
      		react{
		case(w:Int, x:Int ,y:Int)=>	
		def i=w;var j=0;var l=0;var a = new Array[Int](x); var m=0
		for (i<- w to x)
		{
			var t=0; var z=0
			j=i
			for(l<-0 to  y-1)
			{
			t = t+ j*j 
			j=j+1
			}
			z = math.sqrt(t).toInt
			var p =z*z
			if(p == t)
			{
			a(m)=i
  			m=m+1
			}	
		}
	var q=0
	for(q<-0 until a.length)
	{	
		if(a(q)!=0)
		{	
		printActor ! a(q)
		}
		else
		{
		exit()  
              }
	}
	}
       }
}

object Worker{
def main(args: Array[String])
{
Actor1.start()
}
}

object Project1_remote
{
	def main(args: Array[String])
	{
		val n= Integer.parseInt(args(0))
		val k= Integer.parseInt(args(1))
		var i=1
		val t=10 //10 machines
		for (i<- 1 to t)
		{
		var tuple1 =(n*(i-1)/t + 1,n*i/t,k)
		val remoteActor1 = RemoteActor.select(Node("128.227.248.22"+(i-1).toString, 9010),'myActor1) 
		remoteActor1 ! tuple1			
	}
	}
}

object Print_numbers extends Actor {
  def act = {
	  alive(9010)
       register('myActor_print, self)
	println("print server started")	 
	 loop{
    	react {
        case x: Int => println(x)
	case _=>exit()
   	}
 	}
  }
}

object Print{
def main(args: Array[String])
{
Print_numbers.start()
}
}

package scala

class ScalaSort {

  def movesSort(movesNumber: Array[Int], replayNames: Array[Int], replaysNumber: Int) {

    def swap(i: Int, j: Int) {
      val tempMoves = movesNumber(i)
      movesNumber(i) = movesNumber(j)
      movesNumber(j) = tempMoves

      val tempNames = replayNames(i);
      replayNames(i) = replayNames(j);
      replayNames(j) = tempNames;
    }

    def bubbleSort(moves: Array[Int]) {

      for (i <- 0 to replaysNumber - 1) {
        for (j <- 0 to replaysNumber - 1) {
          if (moves(i) < moves(j)) {
            swap(i, j);
          }
        }
      }
    }
    bubbleSort(movesNumber);
  }

  def scoreSort(scoreNumber: Array[Int], replayNames: Array[Int]) {

    def swap(i: Int, j: Int) {
      val tempScore = scoreNumber(i)
      scoreNumber(i) = scoreNumber(j)
      scoreNumber(j) = tempScore

      val tempNames = replayNames(i);
      replayNames(i) = replayNames(j);
      replayNames(j) = tempNames;
    }

    def quickSort(low: Int, high: Int) {
      val pivot = scoreNumber((low + high) / 2)
      var i = low
      var j = high
      while (i <= j) {
        while (scoreNumber(i) > pivot) i += 1
        while (scoreNumber(j) < pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (low < j) quickSort(low, j)
      if (j < high) quickSort(i, high)
    }
    quickSort(0, scoreNumber.length - 1)
  }

  def frequentMove(movesArrayTmp: Array[Int]): Array[Int] = {
    val movesArray = movesArrayTmp.toList

    val moveU = movesArray.count { tmp => tmp == 'U' }
    val moveR = movesArray.count { tmp => tmp == 'R' }
    val moveD = movesArray.count { tmp => tmp == 'D' }
    val moveL = movesArray.count { tmp => tmp == 'L' }
    val moveType = Array(moveU, moveR, moveD, moveL)
    moveType
  }

  def movesNumber(movesArrayTmp: Array[Int]): Int = {
    cutArray(movesArrayTmp).length
  }

  def cutArray(movesArrayTmp: Array[Int]): Array[Int] = {
    val movesArray = movesArrayTmp.toList.filter { tmp => tmp != -1 }
    movesArray.toArray[Int]
  }

  def matchMoves(move: Int): String = move match {
    case 'U' => "Move Up"
    case 'L' => "Move Left"
    case 'D' => "Move Down"
    case 'R' => "Move Right"
    case 'W' => "Win"
    case 'F' => "Fail"
  }
}
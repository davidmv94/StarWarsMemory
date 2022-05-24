package com.david.starwarsmemory.usecases.medium

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel
import com.david.starwarsmemory.usecases.OnFragmentActionsListener
import com.david.starwarsmemory.R
import com.david.starwarsmemory.databinding.FragmentMediumBinding
import com.david.starwarsmemory.model.Card
import com.david.starwarsmemory.util.Constants

//Properties
lateinit var arrayCards : MutableList<Card>
lateinit var arrayImageViews : Array<ImageView>
lateinit var myListener : OnFragmentActionsListener
lateinit var myResources: Resources
@SuppressLint("StaticFieldLeak")
lateinit var myContext : Context

var firstFlip = true
var firstCardNumber = 0
var pairsCompleteds = 0
var timerStarted = false
const val WIN_PAIRS = Constants.MEDIUM_TOTAL_PAIRS

class MediumViewModel : ViewModel() {

    fun setupGame(binding : FragmentMediumBinding,
                  listener : OnFragmentActionsListener,
                  resources : Resources,
                  context: Context) {
        arrayCards = mutableListOf(
            Card(Constants.Images.LUKE.image, false),
            Card(Constants.Images.LEIA.image, false),
            Card(Constants.Images.DARTHVADER.image, false),
            Card(Constants.Images.YODA.image, false),
            Card(Constants.Images.C3PO.image, false),
            Card(Constants.Images.R2D2.image, false),
            Card(Constants.Images.BOBAFETT.image, false),
            Card(Constants.Images.STORM.image, false),
            Card(Constants.Images.STORM2.image, false),
            Card(Constants.Images.ANAKIN.image, false),
            Card(Constants.Images.OBIWAN.image, false),
            Card(Constants.Images.LIGHTSABER.image, false),
            Card(Constants.Images.LUKE.image, false),
            Card(Constants.Images.LEIA.image, false),
            Card(Constants.Images.DARTHVADER.image, false),
            Card(Constants.Images.YODA.image, false),
            Card(Constants.Images.C3PO.image, false),
            Card(Constants.Images.R2D2.image, false),
            Card(Constants.Images.BOBAFETT.image, false),
            Card(Constants.Images.STORM.image, false),
            Card(Constants.Images.STORM2.image, false),
            Card(Constants.Images.ANAKIN.image, false),
            Card(Constants.Images.OBIWAN.image, false),
            Card(Constants.Images.LIGHTSABER.image, false)
        )
        arrayImageViews = arrayOf(
            binding.card1,
            binding.card2,
            binding.card3,
            binding.card4,
            binding.card5,
            binding.card6,
            binding.card7,
            binding.card8,
            binding.card9,
            binding.card10,
            binding.card11,
            binding.card12,
            binding.card13,
            binding.card14,
            binding.card15,
            binding.card16,
            binding.card17,
            binding.card18,
            binding.card19,
            binding.card20,
            binding.card21,
            binding.card22,
            binding.card23,
            binding.card24
        )
        arrayCards.shuffle()

        myListener = listener
        myResources = resources
        myContext = context
    }

    fun setClickableCards(clickable : Boolean) {
        if(clickable){
            arrayImageViews.forEach { imageView ->
                imageView.setOnClickListener {
                    flipCard(it.tag.toString().toInt()-1)
                }
            }
        }else{
            arrayImageViews.forEach { it.setOnClickListener {  } } }
    }

    private fun flipCard(cardNumber : Int) {

        //FIRST CARD
        if (!arrayCards[cardNumber].isFlipped && firstFlip) {

            if(!timerStarted){
                timerStarted = true
                myListener.startCountdown()
            }

            showCard(cardNumber)
            firstCardNumber = cardNumber
            firstFlip = false
            setCardDelay()
        }
        //SECOND CARD
        else if (!arrayCards[cardNumber].isFlipped && !firstFlip){

            showCard(cardNumber)
            firstFlip = true
            incrementMoves()

            if(arrayCards[cardNumber].image == arrayCards[firstCardNumber].image){
                pairCompleted()
            }else{
                pairFailed(cardNumber)
            }
        }
    }

    private fun pairCompleted() {
        pairsCompleteds++
        myListener.completePair()

        if(pairsCompleteds == WIN_PAIRS){
            myListener.createWinDialog()
        }

        setCardDelay()
    }

    private fun incrementMoves(){
        myListener.incrementMoves()
    }

    private fun setCardDelay(){
        setClickableCards(false)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                setClickableCards(true)
            }, Constants.CARD_DELAY
        )
    }

    private fun showCard(cardNumber : Int){
        val card = arrayImageViews[cardNumber]
        val image = arrayCards[cardNumber].image
        card.setImageResource(image)
        card.background = ResourcesCompat.getDrawable(myResources,R.drawable.card_front, null)
        arrayCards[cardNumber].isFlipped = true
    }

    private fun pairFailed(cardNumber: Int){
        val card = arrayImageViews[cardNumber]

        setClickableCards(false)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                card.background = ResourcesCompat.getDrawable(myResources,R.drawable.card_back, null)
                card.setImageResource(R.drawable.starwars_logo)
                arrayCards[cardNumber].isFlipped = false

                arrayImageViews[firstCardNumber].background = ResourcesCompat.getDrawable(myResources,R.drawable.card_back, null)
                arrayImageViews[firstCardNumber].setImageResource(R.drawable.starwars_logo)
                arrayCards[firstCardNumber].isFlipped = false

                setClickableCards(true)

            }, Constants.FAIL_DELAY
        )
    }

}
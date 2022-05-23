package com.david.starwarsmemory

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.david.starwarsmemory.databinding.FragmentMediumBinding
import com.david.starwarsmemory.model.Card
import com.david.starwarsmemory.util.Constants
import com.david.starwarsmemory.util.Constants.CARD_DELAY
import com.david.starwarsmemory.util.Constants.FAIL_DELAY


class MediumFragment : Fragment() {

    private var listener: OnFragmentActionsListener? = null
    private var _binding: FragmentMediumBinding? = null
    private val binding get() = _binding!!

    private lateinit var arrayCards : MutableList<Card>
    private lateinit var arrayImageViews : Array<ImageView>

    private var firstFlip = true
    private var firstCardNumber = 0
    private var pairsCompleteds = 0
    private var timerStarted = false

    private val WIN_PAIRS = Constants.MEDIUM_TOTAL_PAIRS


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMediumBinding.inflate(inflater, container, false)

        setArrays()
        setClickableCards(true)

        return binding.root
    }

    private fun setArrays() {
        arrayCards = mutableListOf<Card>(
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
    }

    private fun setClickableCards(clickable : Boolean) {
        if(clickable){
            arrayImageViews.forEach { it.setOnClickListener { flipCard(it.tag.toString().toInt()-1) }}
        }else{
            arrayImageViews.forEach { it.setOnClickListener {  } } }
    }

    private fun flipCard(cardNumber : Int) {

        //FIRST CARD
        if (!arrayCards[cardNumber].isFlipped && firstFlip) {
            clickSound()

            if(!timerStarted){
                timerStarted = true
                listener?.startCountdown()
            }

            showCard(cardNumber)
            firstCardNumber = cardNumber
            firstFlip = false
            setCardDelay()
        }
        //SECOND CARD
        else if (!arrayCards[cardNumber].isFlipped && !firstFlip){
            clickSound()

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

    private fun clickSound() {
        val mp = MediaPlayer.create(requireContext(),R.raw.click_sound)
        mp.start()
    }

    private fun pairCompleted() {
        pairsCompleteds++
        listener?.completePair()

        if(pairsCompleteds == WIN_PAIRS){
            listener?.createWinDialog()
        }

        setCardDelay()
    }

    private fun incrementMoves(){
        listener?.incrementMoves()
    }

    private fun setCardDelay(){
        setClickableCards(false)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                setClickableCards(true)
            },CARD_DELAY)
    }

    private fun showCard(cardNumber : Int){
        val card = arrayImageViews[cardNumber]
        val image = arrayCards[cardNumber].image

        card.setImageResource(image)
        card.background = resources.getDrawable(R.drawable.card_front)
        arrayCards[cardNumber].isFlipped = true
    }

    private fun pairFailed(cardNumber: Int){
        val card = arrayImageViews[cardNumber]

        setClickableCards(false)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                card.background = resources.getDrawable(R.drawable.card_back)
                card.setImageResource(R.drawable.starwars_logo)
                arrayCards[cardNumber].isFlipped = false

                arrayImageViews[firstCardNumber].background = resources.getDrawable(R.drawable.card_back)
                arrayImageViews[firstCardNumber].setImageResource(R.drawable.starwars_logo)
                arrayCards[firstCardNumber].isFlipped = false

                setClickableCards(true)

            }, FAIL_DELAY)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentActionsListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
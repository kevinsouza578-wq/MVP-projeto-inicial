const questions = [
  {
    text: "Qual civilizacao construiu as piramides de Gize?",
    answers: ["Egipcia", "Romana", "Inca", "Grega"],
    correct: "Egipcia"
  },
  {
    text: "Em qual pais ocorreu a Revolucao Francesa?",
    answers: ["Franca", "Portugal", "Espanha", "Italia"],
    correct: "Franca"
  },
  {
    text: "Quem foi conhecido como imperador do Brasil em 1822?",
    answers: ["Dom Pedro I", "Dom Joao VI", "Tiradentes", "Jose Bonifacio"],
    correct: "Dom Pedro I"
  },
  {
    text: "A escrita cuneiforme surgiu principalmente na regiao da:",
    answers: ["Mesopotamia", "Escandinavia", "Polinesia", "Australia"],
    correct: "Mesopotamia"
  }
];

let currentIndex = 0;
let correctCount = 0;
let answered = false;

const questionEl = document.getElementById("question");
const answersEl = document.getElementById("answers");
const feedbackEl = document.getElementById("feedback");
const progressEl = document.getElementById("progress");
const nextButton = document.getElementById("nextButton");

function renderQuestion() {
  answered = false;
  const current = questions[currentIndex];
  questionEl.textContent = current.text;
  progressEl.textContent = "Pergunta " + (currentIndex + 1) + " de " + questions.length;
  feedbackEl.textContent = "";
  nextButton.classList.add("hidden");

  answersEl.innerHTML = "";
  current.answers.forEach((answer) => {
    const button = document.createElement("button");
    button.type = "button";
    button.textContent = answer;
    button.addEventListener("click", () => chooseAnswer(button, answer));
    answersEl.appendChild(button);
  });
}

function chooseAnswer(button, answer) {
  if (answered) return;
  answered = true;

  const current = questions[currentIndex];
  const isCorrect = answer === current.correct;
  if (isCorrect) {
    correctCount += 1;
    feedbackEl.textContent = "Resposta correta.";
    button.classList.add("correct");
  } else {
    feedbackEl.textContent = "Resposta incorreta. Correta: " + current.correct + ".";
    button.classList.add("wrong");
  }

  Array.from(answersEl.children).forEach((child) => {
    child.disabled = true;
    if (child.textContent === current.correct) {
      child.classList.add("correct");
    }
  });

  nextButton.textContent = currentIndex === questions.length - 1 ? "Finalizar" : "Proxima";
  nextButton.classList.remove("hidden");
}

async function finishGame() {
  const score = Math.round((correctCount / questions.length) * 100);
  feedbackEl.textContent = "Enviando " + score + " pontos...";

  try {
    const result = await GameSDK.submitScore({
      gameSlug: "quiz-historia",
      score
    });
    feedbackEl.textContent = "Pontuacao salva. Total atual: " + result.totalScore + ".";
    nextButton.classList.add("hidden");
  } catch (error) {
    feedbackEl.textContent = error.message;
  }
}

nextButton.addEventListener("click", () => {
  if (currentIndex === questions.length - 1) {
    finishGame();
    return;
  }
  currentIndex += 1;
  renderQuestion();
});

renderQuestion();

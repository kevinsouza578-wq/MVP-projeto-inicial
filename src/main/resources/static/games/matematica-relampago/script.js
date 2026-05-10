const rounds = [
  { left: 3, right: 4 },
  { left: 6, right: 7 },
  { left: 8, right: 5 },
  { left: 9, right: 3 },
  { left: 7, right: 8 }
];

let index = 0;
let correct = 0;

const problemEl = document.getElementById("problem");
const progressEl = document.getElementById("progress");
const scorePreviewEl = document.getElementById("scorePreview");
const feedbackEl = document.getElementById("feedback");
const form = document.getElementById("answerForm");
const answerInput = document.getElementById("answer");

function renderRound() {
  const round = rounds[index];
  problemEl.textContent = round.left + " x " + round.right;
  progressEl.textContent = "Rodada " + (index + 1) + " de " + rounds.length;
  scorePreviewEl.textContent = "Acertos: " + correct;
  answerInput.value = "";
  answerInput.focus();
}

async function finishGame() {
  const score = correct * 24;
  feedbackEl.textContent = "Fim do desafio. Enviando " + score + " pontos...";
  form.querySelector("button").disabled = true;
  answerInput.disabled = true;

  try {
    const result = await GameSDK.submitScore({
      gameSlug: "matematica-relampago",
      score
    });
    feedbackEl.textContent = "Pontuacao salva. Total atual: " + result.totalScore + ".";
  } catch (error) {
    feedbackEl.textContent = error.message;
  }
}

form.addEventListener("submit", (event) => {
  event.preventDefault();
  const round = rounds[index];
  const expected = round.left * round.right;
  const answer = Number(answerInput.value);

  if (answer === expected) {
    correct += 1;
    feedbackEl.textContent = "Correto.";
  } else {
    feedbackEl.textContent = "Incorreto. Resposta: " + expected + ".";
  }

  index += 1;
  if (index >= rounds.length) {
    finishGame();
    return;
  }

  renderRound();
});

renderRound();

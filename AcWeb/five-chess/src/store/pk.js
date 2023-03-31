

export default {

    state: {
        opponentUsername: "",
        opponentPhoto: "",
        status: "selecting",
        socket: null,
        is_over: 0,
        game: null,
        is_me: 0,
        result: "",
        content: [],
    },
    getters: {
    },
    mutations: {
        resetContent(state, content) {
            state.content = content;
        },
        updateContent(state, message) {
            state.content.push(message);
        },
        updateResult(state, result) {
            state.result = result;
        },
        updateIsme(state, st) {
            state.is_me = st;
        },
        updateIsover(state, is_over) {
            state.is_over = is_over;
        },
        updateBoard(state, board) {
            state.game = board;
        },
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponentUsername = opponent.username;
            state.opponentPhoto = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        }


    },
    actions: {



    },
    modules: {
    }



}
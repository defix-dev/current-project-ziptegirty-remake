--
-- PostgreSQL database dump
--

-- Dumped from database version 16.4
-- Dumped by pg_dump version 16.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: chat_keys; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chat_keys (
    user_id integer NOT NULL,
    private_key text,
    public_key text NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.chat_keys OWNER TO postgres;

--
-- Name: chat_keys_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.chat_keys_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.chat_keys_user_id_seq OWNER TO postgres;

--
-- Name: chat_keys_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chat_keys_user_id_seq OWNED BY public.chat_keys.user_id;


--
-- Name: chats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chats (
    id integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.chats OWNER TO postgres;

--
-- Name: chats_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.chats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.chats_id_seq OWNER TO postgres;

--
-- Name: chats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chats_id_seq OWNED BY public.chats.id;


--
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    id integer NOT NULL,
    sender_id integer NOT NULL,
    chat_id integer NOT NULL,
    message text NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- Name: messages_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.messages_id_seq OWNER TO postgres;

--
-- Name: messages_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(64) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(64) NOT NULL,
    password character varying(255) NOT NULL,
    created_at date DEFAULT now()
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_chats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_chats (
    user_id integer NOT NULL,
    chat_id integer NOT NULL
);


ALTER TABLE public.users_chats OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_roles (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.users_roles OWNER TO postgres;

--
-- Name: chat_keys user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chat_keys ALTER COLUMN user_id SET DEFAULT nextval('public.chat_keys_user_id_seq'::regclass);


--
-- Name: chats id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats ALTER COLUMN id SET DEFAULT nextval('public.chats_id_seq'::regclass);


--
-- Name: messages id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages ALTER COLUMN id SET DEFAULT nextval('public.messages_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: chat_keys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chat_keys (user_id, private_key, public_key, created_at) FROM stdin;
1	IqC9lLs5+8c7XQmgoAeVl0XwD9E72/UN9kzUBTBE6zoUIOpMEqmseinp4ezcOO+0UJqGA+koW0OMFZt+afJtQzuEGxmQERu0yila6EMIen2D0LRXMkLpkalblD6l/tw6hRpMEZQMZ9Nq4az14l6Qd8V7dUuyKsJ7bKmhkVCqy5rEm8eV1ZrlYG46b20dGop3vtSmjsCgT0naI/rVGiE7fwTjjASeCQWRLIPBlvCRSB6iqTgH5uu5POsotyPcJh+fb8Vsxc72VBxNhIxY1n76h4bZOY/HbYmI8Swq+QMO9qNpogXi2iLV3lvnH74bUmFZkuinf7FbtVAmnPRvTgbVZ5+B8FOYZFC0Je90S6O9z0c=	zYKnZ2lkPvKs0bRveiWyCyM1y+uHfWS3DgrrSKCTYLoNL07H7Rz0x3V9hfTnugd/zKIzP4zSyXJlJL+ozvkteJSgUy2k8Bcs+E3VD8VCq6vOQEgqXbzGACgLL8VnBQyNcWCBJjrvIxunis7oTFC7zZH9wzHlDgOmuxmfFBWcw1k=	2025-02-26 23:13:52.085886
6	xL4kEm2VCXvbW/ArJzZc2hwDPB+2IUfwWXmcT12KPqSWiPNosTtJ1VcF8YfueW0NHg/K2l71IE1XalERBBCsAZT3nPRNDfkA4+3R+wTsJwv11S4hinRFf602xqfjWj2DwLIz4U/INPhuNK6sWPWf+h1uyxV5p0eHzRw0ZeUkpBVKI+/FRcd1NqYylwh2W7Y9pm0RnNsfSYrHqVtcM9ltCbsw0KtNjer6BiNsm7OcUKJhuLfIw16vi2kCv9tgkqypFwZJvbCpNY0H3TylD7xeXGmhlRwgu+c6KoTHNKgGZmknSEsL0HJmJ0RcqZo52cS6rfd3+EIOUPxwuzfmRtOb4PAW3N+XM4UhZrOPRCY6vtI=	zYKnZ2lkPvKs0bRveiWyCyM1y+uHfWS3DgrrSKCTYLrdTFLR0CKeQ5/zdDLhKwm/AdjZmIn7uVQz1y3GRaIdbz6lEEtMebiug9WcWKofqYkrhHz6xBTI+CgbeU1RTQ/plLeXWCBFzk46GXcoJldxCJZSOwuaKTufEpD0WdedGPc=	2025-02-26 23:13:59.365611
9	Jj9KLBtPAWACEGoFqdZb2I3xzxxtEVWdf2S7irZh2OOuPJ4VXUCqjht7dFGmn9tZvRM3H4pHMcjES8OAfgQLKdkDjvnvN4Mc3vvPVE3KN1n0dWRT8UYE+GYSlNpJa4+24jt7AOgfDD8b8SzYHdco0Qs/avZJgKyqBZ3tL27W23n9hLZbuI1jkMnW34TwIat+UmWnj6IU6yTtEtZXQVp7TedCHtmNkcPFnH6H0L2hXZuM3O82tSXFioCFZ4G43cX5AasoNGQf1Aj4Yy0lDbNgdInwcdXmHlERaBzw7+JtjZAP/IfmioinntsrqUFQyhGeUWRxWiwFDDx1+NxWDtbejame6Nli+5lk1EnC7yQUMx4=	zYKnZ2lkPvKs0bRveiWyCyM1y+uHfWS3DgrrSKCTYLrVF1d3tmqAMSGpTLer/aez32itlvaos1WvS1hnhHuReoM0cW7ZuPrjonD0P/+/z/VZQLek9JujUZmPDlAje/y05woKMcEv4+ZGMlRUD0zJlVgSsQVFqkvFNCUNyQUfSm8=	2025-02-27 18:44:50.96097
14	31k5dm3EMwkhNZXG3uCVyREyFowhge0fCuoqBj6/G70auVjXbDxUeCEV3S5mIruQJKGM/oyOO2xep5Jq89dXsLiS2yAeR2phW+AOF3TUQMEVQBGl6qcJ7hLlwQfZARzf/7BOdrHKx3PGwqVAMIiJqa4ig8r7FPFv7BkCylyptRYwlfyhLAdYnpRkNif1coxuMxabbpUY+c7Vl0hhddQRKTUQwDP1an1vh9kke95nUlLJGYZYMKdeMojnjBT7V/wQm2EyVLmIocBFgRoyF9T+nhbDQkTrtNQ1tdJqZV3lVmnBP+K9kb44pF65CBToCP/khSL+B2nwevNn+POVYd7k0FFEq5XfF6EjquSphHyhxZc=	zYKnZ2lkPvKs0bRveiWyCyM1y+uHfWS3DgrrSKCTYLq9Er7n4qFdg+ff+zw0RDd2rX66KDUYw1hmHIV8T9gilszkMU3c9TlAH6opSJTP9fbB9OjfXQvKvbML9xXicqmiX5do8s9OBTcZPVzEdIqXM8AjOpjIdFtG8PDEEyzhOt0=	2025-03-13 17:55:31.92843
\.


--
-- Data for Name: chats; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chats (id, created_at) FROM stdin;
15	2025-02-26 23:14:14.525141
16	2025-02-27 18:45:43.937645
17	2025-03-13 17:56:20.732342
18	2025-03-13 17:56:32.65116
\.


--
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.messages (id, sender_id, chat_id, message, created_at) FROM stdin;
126	1	15	KGxiQ2yGpTdBsblb2Nuy4GR3fzka0VwgrF8ffgpAyO6uinKGFFhzWJpf/17fwPZP	2025-02-26 23:14:14.550769
127	6	15	ce00p/CmEBPc/WvnBTYFBfpm40K3iHe8/frMW949KvdWB9X3znI4YRXK3dAT+S93	2025-02-26 23:32:56.503098
128	1	15	VIbhDx0IdlFPydNlEAi0u0sB1FpDJlV5+lmf+kc6LZbw84vp9Id9H32CENnnqpjM	2025-02-26 23:33:13.808935
129	6	15	RYK85acD3Uv4QDN6wL86sasb7kW+konZeJlyzuXNDJwJ59sKD2fWkxGXXFiYIHRn	2025-02-26 23:33:21.382887
130	6	15	tm39cFV5vpsgL+CmTypP3XvcPuBDNwcuoP+jWHzZKwM=	2025-02-26 23:36:14.096864
131	1	15	mGj9ykHAKx3JAqGt0RCzzw8wiXBydLAHU2gW3eVXDBQ=	2025-02-26 23:36:47.136708
132	1	15	0/3AGnPlj3aP+CSMEFztDRlBh9SZ3ng+u43XsW92doxNZBzsN2yRDEdAjoY730c7	2025-02-27 16:42:32.76892
133	9	16	TP7krsb9cb/U/BCpXHdIZ3k3YaAHu3lwHKqtseJpAtrzin+LeujibFq09TsvjF68	2025-02-27 18:45:43.974645
134	9	16	N7m5j9O+2t+xw47BfiI+Loe2iGYw2hfeNSo6sX0zaqbmh8a8yVLlBu5ax/cURmZA	2025-02-27 18:45:56.338552
135	1	16	mCkmhsbYbSrcDR/WfS2FV+vB8zyCFRLpVQNC1J+7JXs=	2025-02-27 18:46:20.668299
136	9	16	XqgHx4+0rka3676GlkZS4mg1G6fvc0ZeWhMOHSfckQo=	2025-02-27 18:46:30.193883
137	1	15	OjVZvXAJuR/d8o7UMIVxRwhULqMqwpt4P+GCSsCJj1hWJZlDfSdMA8G8PheZyMDI	2025-02-27 20:06:19.862135
138	1	15	HNIGjOCQ9ZGAqFL4HILvu0EIcVKzqdRRlMOgVmOTbHg=	2025-03-06 13:27:02.462517
139	1	16	LdxXuEbnZqTngtbc+9/lIR805HrtnRXf2f18VpXTIi4=	2025-03-12 17:18:57.280633
140	14	17	/21jcr885IVSSoUaiQNbHwD7oNyFMZdlwNumOHKVfWmPM1U2Gcn4eFh+xPES0Q8T	2025-03-13 17:56:20.767349
141	14	18	hH/m8qQ4YjXxmP0v6ZSV4vvrA0uhav9NBcgqF+wNtenraei8qBDTKP9PiH1DeZIZ	2025-03-13 17:56:32.67019
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, name) FROM stdin;
1	USER
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, username, password, created_at) FROM stdin;
1	defix	$2a$10$.9aLbJ3/koU1R3NxivqPzO58o76fn/nlqQAnoO8gNKNf.0XNwRFW6	2025-01-27
2	defixx	$2a$10$jlFveyjsJ1VCCU4iHnX/Hu0ernW5CNSeGTi5flVnP0FmN7SaTs3jK	2025-01-27
3	mlg	$2a$10$65Wkl7RQ.oTN0ZCOrXJXa.usl7PVRba7nZaC.GlOob4sVwzzYXwjO	2025-02-05
4	defix_test	$2a$10$0jG1Onb.1LmuMC4445n4Bexx7i5gcYjRonMHhRiiN6ei87rI7zYQO	2025-02-05
5	defix_test2	$2a$10$LKApT0UdxuUTzxoBEKlBwuIs2PIuL/BbSzIStvjjqQJwqs91YpSnq	2025-02-05
6	defix_test3	$2a$10$odTd7hnRN/TDYyAdgFXhTeYlBjLXOkPer5oTanI1OnXCA/7mCLO8.	2025-02-05
7	defix_test4	$2a$10$YoYUNNCt2JN8RhlUntGJoOH1TX8pt/26XFhLDeSU8708nu5u7XbHe	2025-02-05
8	defix_test5	$2a$10$ovo/Y92vYLmGVL6/MSLUKOPXLzKCsOiP9ybqgtqSCB31bN/Rlp2Xa	2025-02-05
9	test	$2a$10$aQBCNxdimA8nFznfhXRlTOgwFB.cX0Ll/.9T.QLG.wIT.oNerzhuK	2025-02-06
10	defix11	$2a$10$1VJIB1fJhCbIMuCuhSmeGe/2qKJilDSnnyP6FuAkOZhEbVZx2lmcO	2025-02-06
11	bh000	$2a$10$F5/I3j5y/OjhMYRXNeKj8O9m5P5r9y2K.mT.0RN0ACsDcEwHnfa3G	2025-02-06
12	defix-dev	$2a$10$.ksS/bmGkqnXhl6HRFavLu/Kh3S0K21/oDTrIOkI1I.YCOKFXDu2O	2025-02-27
13	gregre	$2a$10$aKXigq1gbhV1I.M1Qs/xqO1wRmZLvVSVW7yZbRSU.gcgS4LdY0Ada	2025-02-27
14	my_user	$2a$10$PfzmtDm7AJ1mCNKqXcH7F.odwbgTrfwVy3SBbWb80lM7hlo/AhtS.	2025-03-13
\.


--
-- Data for Name: users_chats; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_chats (user_id, chat_id) FROM stdin;
6	15
1	15
9	16
1	16
14	17
6	17
14	18
1	18
\.


--
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_roles (user_id, role_id) FROM stdin;
1	1
3	1
4	1
5	1
6	1
7	1
8	1
9	1
10	1
11	1
12	1
13	1
14	1
\.


--
-- Name: chat_keys_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chat_keys_user_id_seq', 1, false);


--
-- Name: chats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chats_id_seq', 18, true);


--
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_id_seq', 141, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 1, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 14, true);


--
-- Name: chat_keys chat_keys_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chat_keys
    ADD CONSTRAINT chat_keys_pkey PRIMARY KEY (user_id);


--
-- Name: chats chats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chats
    ADD CONSTRAINT chats_pkey PRIMARY KEY (id);


--
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: users_chats users_chats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_chats
    ADD CONSTRAINT users_chats_pkey PRIMARY KEY (user_id, chat_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: idx_messages_chat_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_messages_chat_id ON public.messages USING btree (chat_id);


--
-- Name: idx_messages_sender_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_messages_sender_id ON public.messages USING btree (sender_id);


--
-- Name: idx_users_chats_chat_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_chats_chat_id ON public.users_chats USING btree (chat_id);


--
-- Name: idx_users_chats_user_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_users_chats_user_id ON public.users_chats USING btree (user_id);


--
-- Name: chat_keys chat_keys_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chat_keys
    ADD CONSTRAINT chat_keys_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;


--
-- Name: messages messages_chat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES public.chats(id);


--
-- Name: messages messages_sender_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_sender_id_fkey FOREIGN KEY (sender_id) REFERENCES public.users(id);


--
-- Name: users_chats users_chats_chat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_chats
    ADD CONSTRAINT users_chats_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES public.chats(id);


--
-- Name: users_chats users_chats_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_chats
    ADD CONSTRAINT users_chats_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: users_roles users_roles_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: users_roles users_roles_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

